package com.sants.nexus_stock_api.services.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sants.nexus_stock_api.domain.product.Category;
import com.sants.nexus_stock_api.dto.product.CategoryResponseDTO;
import com.sants.nexus_stock_api.repositories.product.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO create(String name) {

        if (categoryRepository.existsByName(name)) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);

        Category saved = categoryRepository.save(category);

        return toDTO(saved);
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CategoryResponseDTO update(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    
        if (categoryRepository.existsByName(name) && !category.getName().equals(name)) {
            throw new RuntimeException("Category already exists");
        }
    
        category.setName(name);
    
        Category updated = categoryRepository.save(category);
    
        return toDTO(updated);
    }
    
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    
        categoryRepository.delete(category);
    }

    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return toDTO(category);
    }

    private CategoryResponseDTO toDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }
}
