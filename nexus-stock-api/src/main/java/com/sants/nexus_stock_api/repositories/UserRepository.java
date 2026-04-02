package com.sants.nexus_stock_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.sants.nexus_stock_api.domain.user.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);
}
