package com.sants.nexus_stock_api.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sants.nexus_stock_api.domain.product.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByCnpj(String cnpj);
}
