package org.example.backend.service;

import org.example.backend.exceptions.WarehouseAppException;
import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.WarehouseRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepo warehouseRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;

    public WarehouseService(WarehouseRepo warehouseRepo, ProductRepo productRepo, ProductService productService) {
        this.warehouseRepo = warehouseRepo;
        this.productRepo = productRepo;
        this.productService = productService;
    }

    public List<Warehouse> getAllWarehouses() {
        return  warehouseRepo.findAll();

    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public Warehouse getWarehouseById(String id) {
        return warehouseRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Warehouse not found: " + id));
    }

    public Warehouse updateWarehouse(String id, Warehouse warehouse) {
        Warehouse oldData = warehouseRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Warehouse not found: " + id));
        warehouseRepo.save(
                oldData.
                        withName(warehouse.name()).
                        withCity(warehouse.city()).
                        withStreet(warehouse.street()).
                        withHouseNumber(warehouse.houseNumber()).
                        withZipCode(warehouse.zipCode()));
        return  warehouse;
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
