package com.ecommerce.emarket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.AppRole;
import com.ecommerce.emarket.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(AppRole roleUser);

}
