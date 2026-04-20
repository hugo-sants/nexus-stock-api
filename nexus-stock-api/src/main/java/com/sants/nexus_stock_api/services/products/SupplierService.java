package com.sants.nexus_stock_api.services.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sants.nexus_stock_api.domain.product.Supplier;
import com.sants.nexus_stock_api.dto.product.SupplierCreateDTO;
import com.sants.nexus_stock_api.dto.product.SupplierResponseDTO;
import com.sants.nexus_stock_api.repositories.product.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierResponseDTO create(SupplierCreateDTO dto) {

        if (supplierRepository.existsByCnpj(dto.cnpj())) {
            throw new RuntimeException("CNPJ already exists");
        }

        Supplier supplier = new Supplier();
        supplier.setName(dto.name());
        supplier.setCnpj(dto.cnpj());
        supplier.setEmail(dto.email());
        supplier.setPhone(dto.phone());

        Supplier saved = supplierRepository.save(supplier);

        return toDTO(saved);
    }

    public List<SupplierResponseDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public SupplierResponseDTO update(Long id, SupplierCreateDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    
        if (supplierRepository.existsByCnpj(dto.cnpj()) && !supplier.getCnpj().equals(dto.cnpj())) {
            throw new RuntimeException("CNPJ already exists");
        }
    
        supplier.setName(dto.name());
        supplier.setCnpj(dto.cnpj());
        supplier.setEmail(dto.email());
        supplier.setPhone(dto.phone());
    
        Supplier updated = supplierRepository.save(supplier);
    
        return toDTO(updated);
    }
    
    public void delete(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    
        supplierRepository.delete(supplier);
    }

    public SupplierResponseDTO findById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        return toDTO(supplier);
    }

    private SupplierResponseDTO toDTO(Supplier supplier) {
        return new SupplierResponseDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getCnpj(),
                supplier.getEmail(),
                supplier.getPhone()
        );
    }
}