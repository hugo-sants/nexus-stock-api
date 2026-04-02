package com.sants.nexus_stock_api.dto.register;

import com.sants.nexus_stock_api.domain.user.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {
}
