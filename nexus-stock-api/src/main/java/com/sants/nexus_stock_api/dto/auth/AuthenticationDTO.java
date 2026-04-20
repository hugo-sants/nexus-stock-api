package com.sants.nexus_stock_api.dto.auth;

public record AuthenticationDTO(
    String email, 
    String password
) {}
