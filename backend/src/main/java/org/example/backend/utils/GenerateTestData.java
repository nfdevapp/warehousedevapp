package org.example.backend.utils;

import org.example.backend.model.entities.Customer;
import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Supplier;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.CustomerRepo;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.SupplierRepo;
import org.example.backend.repository.WarehouseRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerateTestData {
    private final WarehouseRepo warehouseRepo;
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;
    private final SupplierRepo supplierRepo;

    public GenerateTestData(WarehouseRepo warehouseRepo, ProductRepo productRepo, CustomerRepo customerRepo, SupplierRepo supplierRepo) {
        this.warehouseRepo = warehouseRepo;
        this.customerRepo = customerRepo;
        this.supplierRepo = supplierRepo;
        this.productRepo = productRepo;
        saveWarehouseTestData();
        saveProductTestData();
        saveCustomerTestData();
        saveSupplierTestData();
    }

    private List<Warehouse> saveWarehouseTestData() {
        List<Warehouse> listWarehouses = List.of(
                Warehouse.builder().id("1").name("Nordlager").city("Hamburg").street("Am Sandtrpark").houseNumber("12").zipCode("20457").build(),
                Warehouse.builder().id("2").name("Zentrallager Ost").city("Berlin").street("Boxhagener Straße").houseNumber("78").zipCode("10245").build(),
                Warehouse.builder().id("3").name("Südlager").city("München").street("Sesamstraße").houseNumber("145").zipCode("80339").build()
        );
        return warehouseRepo.saveAll(listWarehouses);
    }


    private List<Product> saveProductTestData() {
        List<Product> listProducts = List.of(
                Product.builder().id("1").name("Kopfhörer").barcode("421873343001123").description("Over-Ear").quantity(120).customerId("1").supplierId("1").warehouseId("1").build(),
                Product.builder().id("2").name("Fahrrad").barcode("73918420045128").description("Sportgerät").quantity(250).customerId("1").supplierId("2").warehouseId("1").build(),
                Product.builder().id("3").name("Creme").barcode("5908723434349017").description("Gesichtscreme").quantity(180).customerId("1").supplierId("3").warehouseId("1").build(),
                Product.builder().id("4").name("T-Shirt").barcode("8712345678123").description("Baumwolle").quantity(340).customerId("2").supplierId("2").warehouseId("2").build(),
                Product.builder().id("5").name("USB-C Kabel").barcode("400912887645621").description("1m Ladekabel").quantity(500).customerId("2").supplierId("2").warehouseId("2").build(),
                Product.builder().id("6").name("Kopfhörer").barcode("421873443001123").description("Over-Ear").quantity(120).customerId("1").supplierId("1").warehouseId("2").build(),
                Product.builder().id("7").name("Fahrrad").barcode("7391820045128").description("Sportgerät").quantity(250).customerId("3").supplierId("3").warehouseId("3").build(),
                Product.builder().id("8").name("Creme").barcode("590872334359017").description("Gesichtscreme").quantity(180).customerId("3").supplierId("1").warehouseId("3").build(),
                Product.builder().id("9").name("T-Shirt").barcode("8712345678123").description("Baumwolle").quantity(340).customerId("2").supplierId("1").warehouseId("1").build()
        );
        return productRepo.saveAll(listProducts);
    }


    private List<Customer> saveCustomerTestData() {
        List<Customer> listCustomers = List.of(
                Customer.builder().id("1").name("Max Mustermann").city("Berlin").street("Hauptstraße").houseNumber("12").zipCode("10115").build(),
                Customer.builder().id("2").name("Anna Müller").city("Hamburg").street("Reeperbahn").houseNumber("44").zipCode("20359").build(),
                Customer.builder().id("3").name("Peter Schmidt").city("Köln").street("Domstraße").houseNumber("5a").zipCode("50667").build()
        );
        return customerRepo.saveAll(listCustomers);
    }

    private List<Supplier> saveSupplierTestData() {
        List<Supplier> listSuppliers = List.of(
                Supplier.builder().id("1").name("TechLine GmbH").city("Stuttgart").street("Industriestraße").houseNumber("77").zipCode("70173").build(),
                Supplier.builder().id("2").name("FashionStyle AG").city("München").street("Modeweg").houseNumber("10").zipCode("80331").build(),
                Supplier.builder().id("3").name("BeautyWorld KG").city("Düsseldorf").street("Kosmetikallee").houseNumber("21").zipCode("40210").build()
        );
        return supplierRepo.saveAll(listSuppliers);
    }
}
