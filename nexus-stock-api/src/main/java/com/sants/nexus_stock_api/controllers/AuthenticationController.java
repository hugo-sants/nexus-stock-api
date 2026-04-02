package com.sants.nexus_stock_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sants.nexus_stock_api.domain.user.User;
import com.sants.nexus_stock_api.dto.authentication.AuthenticationDTO;
import com.sants.nexus_stock_api.dto.login.LoginResponseDTO;
import com.sants.nexus_stock_api.dto.register.RegisterDTO;
import com.sants.nexus_stock_api.repositories.UserRepository;
import com.sants.nexus_stock_api.security.TokenService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto) {
        
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication auth = this.authManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity registe(@RequestBody RegisterDTO dto) {
        ResponseEntity responseEntity;

        if (this.repository.findByEmail(dto.email()) != null) {
            responseEntity = ResponseEntity.badRequest().build();
        }
        else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
            User newUser = new User(dto.role(), dto.email(), encryptedPassword);
            
            this.repository.save(newUser);

            responseEntity = ResponseEntity.ok().build();
        }

        return responseEntity;
    }
    
    
}
