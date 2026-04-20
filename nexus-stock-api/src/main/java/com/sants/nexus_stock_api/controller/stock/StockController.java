package com.sants.nexus_stock_api.controller.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sants.nexus_stock_api.dto.stock.StockMovementCreateDTO;
import com.sants.nexus_stock_api.dto.stock.StockMovementResponseDTO;
import com.sants.nexus_stock_api.services.stock.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/increase")
    public ResponseEntity<Void> increase(@RequestBody StockMovementCreateDTO dto) {
        stockService.increase(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/decrease")
    public ResponseEntity<Void> decrease(@RequestBody StockMovementCreateDTO dto) {
        stockService.decrease(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/history")
    public ResponseEntity<Page<StockMovementResponseDTO>> history(@RequestParam Long productId,Pageable pageable) {
        return ResponseEntity.ok(stockService.history(productId, pageable));
    }
}