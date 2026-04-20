package com.sants.nexus_stock_api.services.metrics;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sants.nexus_stock_api.dto.product.TopProductDTO;
import com.sants.nexus_stock_api.repositories.metrics.MetricsRepository;

@Service
public class MetricsService {

    @Autowired
    private MetricsRepository metricsRepository;

    public MetricsService(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    public BigDecimal getStockValue() {
        return metricsRepository.getStockValue();
    }

    public Long getLowStockCount() {
        return metricsRepository.countLowStock();
    }

    public Page<TopProductDTO> getTopProducts(Pageable pageable) {
        return metricsRepository.findTopProducts(pageable);
    }
}
