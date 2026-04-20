package com.sants.nexus_stock_api.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.sants.nexus_stock_api.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
    
    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
    
}
