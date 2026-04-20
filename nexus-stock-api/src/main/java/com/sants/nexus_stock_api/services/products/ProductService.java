package com.sants.nexus_stock_api.services.products;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sants.nexus_stock_api.domain.product.Category;
import com.sants.nexus_stock_api.domain.product.Product;
import com.sants.nexus_stock_api.domain.product.Supplier;
import com.sants.nexus_stock_api.dto.product.ProductCreateDTO;
import com.sants.nexus_stock_api.dto.product.ProductResponseDTO;
import com.sants.nexus_stock_api.dto.product.ProductUpdateDTO;
import com.sants.nexus_stock_api.repositories.product.CategoryRepository;
import com.sants.nexus_stock_api.repositories.product.ProductRepository;
import com.sants.nexus_stock_api.repositories.product.ProductSpecification;
import com.sants.nexus_stock_api.repositories.product.SupplierRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private  SupplierRepository supplierRepository;

    public ProductResponseDTO create(ProductCreateDTO dto) {

        Category category = categoryRepository.findById(dto.categoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        Supplier supplier = supplierRepository.findById(dto.supplierId())
            .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setCostPrice(dto.costPrice());
        product.setSalePrice(dto.salePrice());
        product.setMinStock(dto.minStock());
        product.setQuantity(dto.quantity());
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setActive(true);

        Product saved = productRepository.save(product);

        return toDTO(saved);
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return toDTO(product);
    }

    public Page<ProductResponseDTO> findAll(String name, BigDecimal costPrice, BigDecimal salePrice, Boolean active, Pageable pageable) {
        Boolean activeFilter = (active != null) ? active : true;

        Specification<Product> spec = Specification
                .where(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasCostPrice(costPrice))
                .and(ProductSpecification.hasSalePrice(salePrice))
                .and(ProductSpecification.hasActive(activeFilter));
    
        return productRepository.findAll(spec, pageable)
                .map(this::toDTO);
    }

    public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
            
        Supplier supplier = supplierRepository.findById(dto.supplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        product.setDescription(dto.description());
        product.setCostPrice(dto.costPrice());
        product.setSalePrice(dto.salePrice());
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setMinStock(dto.minStock());

        return toDTO(productRepository.save(product));
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }
    
    public void deactivate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setActive(false);
        productRepository.save(product);
    }

    public void activate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setActive(true);
        productRepository.save(product);
    }

    private ProductResponseDTO toDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCostPrice(),
                product.getSalePrice(),
                product.getMinStock(),
                product.getQuantity(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getSupplier() != null ? product.getSupplier().getId() : null,
                product.getActive()
        );
    }
}
