package org.example.backend.service;

import org.example.backend.exceptions.WarehouseAppException;
import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.SupplierRepo;
import org.example.backend.repository.WarehouseRepo;
import org.example.backend.utils.enums.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepo warehouseRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;

    public WarehouseService(WarehouseRepo warehouseRepo, ProductService productService, ProductRepo productRepo, ProductService productService1) {
        this.warehouseRepo = warehouseRepo;
        this.productRepo = productRepo;
        this.productService = productService1;
    }

    public List<Warehouse> getAllWarehouses() {
        return  warehouseRepo.findAll();

    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public Warehouse getWarehouseById(String id) {
        return warehouseRepo.findById(id).orElse(null);
    }

    public void deleteWarehouse(String id) {
        Warehouse warehouse = warehouseRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Warehouse not found: " + id));

        List<Product> products = productRepo.findByWarehouseId(id);

        for (Product product : products) {
            productService.deleteProduct(product.id());
        }

        warehouseRepo.delete(warehouse);
    }


}
