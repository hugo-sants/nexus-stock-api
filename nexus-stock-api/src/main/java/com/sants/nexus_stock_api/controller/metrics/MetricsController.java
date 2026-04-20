package com.sants.nexus_stock_api.controller.metrics;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sants.nexus_stock_api.dto.product.TopProductDTO;
import com.sants.nexus_stock_api.services.metrics.MetricsService;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stock-value")
    public ResponseEntity<BigDecimal> getStockValue() {
        return ResponseEntity.ok(metricsService.getStockValue());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/low-stock")
    public ResponseEntity<Long> getLowStock() {
        return ResponseEntity.ok(metricsService.getLowStockCount());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-products")
    public ResponseEntity<Page<TopProductDTO>> getTopProducts(Pageable pageable) {
        return ResponseEntity.ok(metricsService.getTopProducts(pageable));
    }
}
