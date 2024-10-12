package com.ecommerce.emarket.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.emarket.model.AppRole;
import com.ecommerce.emarket.model.Role;
import com.ecommerce.emarket.model.User;
import com.ecommerce.emarket.repositories.RoleRepository;
import com.ecommerce.emarket.repositories.UserRepository;
import com.ecommerce.emarket.security.jwt.AuthEntryPoint;
import com.ecommerce.emarket.security.jwt.AuthtokenFilter;
import com.ecommerce.emarket.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthEntryPoint unauthorizedHandler;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthtokenFilter jwAuthtokenFilter() {
        return new AuthtokenFilter();
    }

    @Bean
    // DaoProvider uses userdetailsservice's loadbyusername to load user from db
    // and uses passwordEconder to verify password
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenicationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        // .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api/test**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .anyRequest().authenticated());

        // Add the custom provider, otherwise, would use default dao provider
        // which uses UserDetails and UserdetailsService (default)
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwAuthtokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(
                "v2/api-docs",
                "/confirgurarion/ui",
                "swagger-resources",
                "/configuration/security",
                "swagger-ui.html",
                "/h2-console/**",
                "/webjars/**"));
    }

    @Bean
    public CommandLineRunner seedRoles(UserDetailsServiceImpl userDetailsService) {
        return (args) -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER).orElseGet(() -> {
                Role newUserRole = new Role();
                newUserRole.setRoleName(AppRole.ROLE_USER);
                return roleRepository.save(newUserRole);
            });
            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN).orElseGet(() -> {
                Role newAdminRole = new Role();
                newAdminRole.setRoleName(AppRole.ROLE_ADMIN);
                return roleRepository.save(newAdminRole);
            });
            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER).orElseGet(() -> {
                Role newsellerRole = new Role();
                newsellerRole.setRoleName(AppRole.ROLE_SELLER);
                return roleRepository.save(newsellerRole);
            });

            // Create user user
            if (!userRepository.existsByUsername("user1")) {
                userRepository.save(
                        new User("user1", "user1@example.com", passwordEncoder().encode("password")));
            }

            // Create admin
            if (!userRepository.existsByUsername("admin1")) {
                userRepository.save(
                        new User("admin1", "admin1@example.com", passwordEncoder().encode("password")));
            }

            // Create seller
            if (!userRepository.existsByUsername("seller1")) {
                userRepository.save(
                        new User("seller1", "seller1@example.com", passwordEncoder().encode("password")));
            }

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> adminRoles = Set.of(adminRole);
            Set<Role> sellerRoles = Set.of(sellerRole);

            User user = userRepository.findByUsername("user1").get();
            user.setRoles(userRoles);
            userRepository.save(user);

            User admin = userRepository.findByUsername("admin1").get();
            admin.setRoles(adminRoles);
            userRepository.save(admin);

            User seller = userRepository.findByUsername("seller1").get();
            seller.setRoles(sellerRoles);
            userRepository.save(seller);

        };
    }

}
