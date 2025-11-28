package org.example.backend.controller;

import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.WarehouseRepo;
import org.example.backend.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return  warehouseService.getAllWarehouses();
    }

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return  warehouseService.createWarehouse(warehouse);
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable String id) {
        return warehouseService.getWarehouseById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouse(@PathVariable String id) {
        warehouseService.deleteWarehouse(id);
    }
}
