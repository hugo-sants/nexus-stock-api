package com.sants.nexus_stock_api.dto.product;

import java.math.BigDecimal;

public record ProductUpdateDTO(
    String description,
    BigDecimal costPrice,
    BigDecimal salePrice,
    Long categoryId,
    Long supplierId,
    Integer minStock
) {}
