package com.sants.nexus_stock_api.dto.user;

import com.sants.nexus_stock_api.domain.user.UserRole;

public record UserResponseDTO(
    Long id,
    UserRole role,
    String email,
    String name,
    String cpf,
    Boolean active
) {}