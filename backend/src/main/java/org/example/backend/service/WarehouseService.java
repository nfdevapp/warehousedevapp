package org.example.backend.service;

import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.WarehouseRepo;
import org.example.backend.utils.enums.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepo warehouseRepo;

    public WarehouseService(WarehouseRepo warehouseRepo) {
        this.warehouseRepo = warehouseRepo;
        saveTestData();
    }

    public List<Warehouse> getAllWarehouses() {
        return  warehouseRepo.findAll();

    }

    public Warehouse getWarehouseById(String id) {
        return warehouseRepo.findById(id).orElse(null);
    }

    public List<Warehouse> saveTestData() {
        List<Warehouse> listWarehouses = List.of(
                new Warehouse("1", "Nordlager", "Hamburg", "Am Sandtrpark", "12", "20457"),
                new Warehouse("2", "Zentrallager Ost", "Berlin", "Boxhagener Straße", "78", "10245"),
                new Warehouse("3", "Südlager", "München", "Sesamstraße", "145", "80339")
        );
        return warehouseRepo.saveAll(listWarehouses);
    }


}
