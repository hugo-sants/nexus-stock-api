package com.sants.nexus_stock_api.services.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sants.nexus_stock_api.domain.product.Product;
import com.sants.nexus_stock_api.domain.stock.StockMovement;
import com.sants.nexus_stock_api.domain.stock.StockType;
import com.sants.nexus_stock_api.dto.stock.StockMovementCreateDTO;
import com.sants.nexus_stock_api.dto.stock.StockMovementResponseDTO;
import com.sants.nexus_stock_api.repositories.product.ProductRepository;
import com.sants.nexus_stock_api.repositories.stock.StockMovementRepository;

import jakarta.transaction.Transactional;

@Service
public class StockService {

    @Autowired
    private StockMovementRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void increase(StockMovementCreateDTO dto) {

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setQuantity(product.getQuantity() + dto.quantity());

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setStockType(StockType.IN);
        movement.setQuantity(dto.quantity());
        movement.setReason(dto.reason());

        stockRepository.save(movement);
        productRepository.save(product);
    }

    @Transactional
    public void decrease(StockMovementCreateDTO dto) {

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < dto.quantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - dto.quantity());

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setStockType(StockType.OUT);
        movement.setQuantity(dto.quantity());
        movement.setReason(dto.reason());

        stockRepository.save(movement);
        productRepository.save(product);
    }

    public Page<StockMovementResponseDTO> history(Long productId, Pageable pageable) {
        return stockRepository.findByProductId(productId, pageable)
                .map(this::toDTO);
    }

    private StockMovementResponseDTO toDTO(StockMovement movement) {
        return new StockMovementResponseDTO(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getStockType(),
                movement.getQuantity(),
                movement.getReason(),
                movement.getCreatedAt()
        );
    }
}