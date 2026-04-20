package com.sants.nexus_stock_api.dto.stock;

import java.time.LocalDateTime;

import com.sants.nexus_stock_api.domain.stock.StockType;

public record StockMovementResponseDTO(
    Long id,
    Long productId,
    StockType type,
    Integer quantity,
    String reason,
    LocalDateTime createdAt
) {}
