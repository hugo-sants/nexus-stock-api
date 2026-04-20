package com.sants.nexus_stock_api.dto.product;

import java.math.BigDecimal;

public record ProductCreateDTO(
    String name,
    String description,
    BigDecimal costPrice,
    BigDecimal salePrice,
    Integer minStock,
    Integer quantity,
    Long categoryId,
    Long supplierId
) {}