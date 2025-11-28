package org.example.backend.service;

import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Supplier;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.SupplierRepo;
import org.example.backend.utils.enums.Category;
import org.example.backend.utils.enums.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final SupplierRepo supplierRepo;


    public ProductService(ProductRepo productRepo, SupplierRepo supplierRepo) {
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
        saveTestData();
    }

    public List<Product> getAllProductsById(String warehouseId) {
        return  productRepo.findByWarehouseId(warehouseId);
    }

    public Product getProductById(String id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product updateProduct(String id, Product product) {
        Product oldData = productRepo.findById(id).orElse(null);
        if (oldData != null) {
            productRepo.save(
                    oldData.
                            withName(product.name()).
                            withBarcode(product.barcode()).
                            withCategory(product.category()).
                            withDescription(product.description())
                    );
        }
        return  product;
    }

    public void deleteProduct(String id) {
        productRepo.deleteById(id);
    }

    private String generateBarcode() {
        return UUID.randomUUID().toString();
    }

    public List<Product> saveTestData() {
        List<Product> listProducts = List.of(
                new Product("1","Kopfhörer","421873343001123","Over-Ear",120,Category.ELECTRONICS,"1"),
                new Product("2","Fahrrad","73918420045128","Sportgerät",250,Category.SPORT_EQUIPMENT,"1"),
                new Product("3","Creme","5908723434349017","Gesichtscreme",180,Category.COSMETICS,"1"),
                new Product("4","T-Shirt","8712345678123","Baumwolle",340,Category.CLOTHING,"1"),
                new Product("5","USB-C Kabel","400912887645621","1m Ladekabel",500,Category.ELECTRONICS,"2"),
                new Product("6","Kopfhörer","421873443001123","Over-Ear",120,Category.ELECTRONICS,"2"),
                new Product("7","Fahrrad","7391820045128","Sportgerät",250,Category.SPORT_EQUIPMENT,"2"),
                new Product("8","Creme","590872334359017","Gesichtscreme",180,Category.COSMETICS,"3"),
                new Product("9","T-Shirt","8712345678123","Baumwolle",340,Category.CLOTHING,"3")
        );
        return productRepo.saveAll(listProducts);
    }
}
