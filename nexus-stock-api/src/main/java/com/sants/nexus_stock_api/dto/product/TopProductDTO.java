package com.sants.nexus_stock_api.dto.product;

public record TopProductDTO(
    Long productId,
    String productName,
    Long totalSold
) {}