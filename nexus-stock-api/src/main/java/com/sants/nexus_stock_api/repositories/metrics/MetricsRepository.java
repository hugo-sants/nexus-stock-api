package com.sants.nexus_stock_api.repositories.metrics;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sants.nexus_stock_api.dto.product.TopProductDTO;

public interface MetricsRepository {

    BigDecimal getStockValue();

    Long countLowStock();

    Page<TopProductDTO> findTopProducts(Pageable pageable);
}
