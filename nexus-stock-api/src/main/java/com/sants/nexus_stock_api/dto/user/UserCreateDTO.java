package com.sants.nexus_stock_api.dto.user;

import com.sants.nexus_stock_api.domain.user.UserRole;

public record UserCreateDTO(
    UserRole role,
    String email,
    String password,
    String name,
    String cpf
) {}