package org.example.backend.service;

import org.example.backend.model.entities.Supplier;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.SupplierRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepo supplierRepo;

    public SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    public List<Supplier> getAllSuppliers() {
        return  supplierRepo.findAll();

    }
}
