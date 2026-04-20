package com.sants.nexus_stock_api.repositories.metrics;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sants.nexus_stock_api.dto.product.TopProductDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MetricsRepositoryImpl implements MetricsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BigDecimal getStockValue() {
        return entityManager.createQuery("""
            SELECT COALESCE(SUM(p.quantity * p.costPrice), 0)
            FROM Product p
            WHERE p.active = true
        """, BigDecimal.class).getSingleResult();
    }

    @Override
    public Long countLowStock() {
        return entityManager.createQuery("""
            SELECT COUNT(p)
            FROM Product p
            WHERE p.active = true
            AND p.quantity <= p.minStock
        """, Long.class).getSingleResult();
    }
    @Override
    public Page<TopProductDTO> findTopProducts(Pageable pageable) {

        List<TopProductDTO> content = entityManager.createQuery("""
            SELECT new com.sants.nexus_stock_api.dto.metrics.TopProductDTO(
                p.id,
                p.name,
                SUM(sm.quantity)
            )
            FROM StockMovement sm
            JOIN sm.product p
            WHERE sm.stockType = 'OUT'
            GROUP BY p.id, p.name
            ORDER BY SUM(sm.quantity) DESC
        """, TopProductDTO.class)
        .setFirstResult((int) pageable.getOffset())
        .setMaxResults(pageable.getPageSize())
        .getResultList();

        Long total = entityManager.createQuery("""
            SELECT COUNT(DISTINCT p.id)
            FROM StockMovement sm
            JOIN sm.product p
            WHERE sm.stockType = 'OUT'
        """, Long.class).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }
}
