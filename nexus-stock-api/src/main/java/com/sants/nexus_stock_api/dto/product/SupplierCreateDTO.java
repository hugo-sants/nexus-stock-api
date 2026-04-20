package com.sants.nexus_stock_api.dto.product;

public record SupplierCreateDTO(
    String name,
    String cnpj,
    String email,
    String phone
) {}
