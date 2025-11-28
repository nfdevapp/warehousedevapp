package org.example.backend.service;

import org.example.backend.repository.SupplierRepo;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    private final SupplierRepo supplierRepo;

    public SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }
}
