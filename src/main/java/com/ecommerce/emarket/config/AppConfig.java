package com.ecommerce.emarket.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    // THis Bean/Object will be availbable in the Spring context and an class can
    // use without instatianting ModelMapper
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
