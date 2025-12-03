package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.exceptions.WarehouseAppException;
import org.example.backend.model.dto.WarehouseDto;
import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.WarehouseRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepo warehouseRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;

    public List<Warehouse> getAllWarehouses() {
        return  warehouseRepo.findAll();

    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        Warehouse toSave = Warehouse.builder()
                .name(warehouse.name())
                .city(warehouse.city())
                .street(warehouse.street())
                .houseNumber(warehouse.houseNumber())
                .zipCode(warehouse.zipCode())
                .build(); // ID bleibt null, MongoDB generiert sie automatisch

        return warehouseRepo.save(toSave);
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
