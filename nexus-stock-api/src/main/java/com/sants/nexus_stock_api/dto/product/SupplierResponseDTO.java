package com.sants.nexus_stock_api.dto.product;

public record SupplierResponseDTO(
    Long id,
    String name,
    String cnpj,
    String email,
    String phone
) {}
