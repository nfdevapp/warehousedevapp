package org.example.backend.utils;

import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.service.ProductService;
import org.example.backend.service.WarehouseService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class GenerateTestData {

    private final WarehouseService warehouseService;
    private final ProductService productService;

    public GenerateTestData(WarehouseService warehouseService, ProductService productService) {
        this.warehouseService = warehouseService;
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        if (!warehouseService.getAllWarehouses().isEmpty()) {;
            return;
        }
        createWarehouses();
        createProducts();
    }

    private List<Warehouse> createWarehouses() {

        List<Warehouse> warehouses = List.of(
                Warehouse.builder().name("Nordlager").city("Hamburg").street("Am Sandtrpark")
                        .houseNumber("12").zipCode("20457").build(),
                Warehouse.builder().name("Zentrallager Ost").city("Berlin").street("Boxhagener Straße")
                        .houseNumber("78").zipCode("10245").build(),
                Warehouse.builder().name("Südlager").city("München").street("Sesamstraße")
                        .houseNumber("145").zipCode("80339").build()
        );

        return warehouses.stream()
                .map(warehouseService::createWarehouse)
                .toList();
    }

    private void createProducts() {

        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        String w1 = warehouses.get(0).id();
        String w2 = warehouses.get(1).id();
        String w3 = warehouses.get(2).id();

        List<Product> products = List.of(
                Product.builder().name("Kopfhörer").description("Over-Ear").quantity(120).warehouseId(w1).build(),
                Product.builder().name("Fahrrad").description("Sportgerät").quantity(250).warehouseId(w1).build(),
                Product.builder().name("Creme").description("Gesichtscreme").quantity(180).warehouseId(w1).build(),

                Product.builder().name("T-Shirt").description("Baumwolle").quantity(340).warehouseId(w2).build(),
                Product.builder().name("USB-C Kabel").description("1m Ladekabel").quantity(500).warehouseId(w2).build(),
                Product.builder().name("Kopfhörer").description("Over-Ear").quantity(120).warehouseId(w2).build(),

                Product.builder().name("Fahrrad").description("Sportgerät").quantity(250).warehouseId(w3).build(),
                Product.builder().name("Creme").description("Gesichtscreme").quantity(180).warehouseId(w3).build(),
                Product.builder().name("T-Shirt").description("Baumwolle").quantity(340).warehouseId(w1).build()
        );

        products.forEach(productService::createProduct);
    }
}
