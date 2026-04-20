package com.sants.nexus_stock_api.repositories.product;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.sants.nexus_stock_api.domain.product.Product;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Product> hasCostPrice(BigDecimal costPrice) {
        return (root, query, cb) -> {
            if (costPrice == null) return null;
            return cb.equal(root.get("costPrice"), costPrice);
        };
    }

    public static Specification<Product> hasSalePrice(BigDecimal salePrice) {
        return (root, query, cb) -> {
            if (salePrice == null) return null;
            return cb.equal(root.get("salePrice"), salePrice);
        };
    }

    public static Specification<Product> hasActive(Boolean active) {
        return (root, query, cb) -> {
            if (active == null) return null;
            return cb.equal(root.get("active"), active);
        };
    }
}