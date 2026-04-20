package com.sants.nexus_stock_api.dto.stock;

public record StockMovementCreateDTO(
    Long productId,
    Integer quantity,
    String reason
) {}
