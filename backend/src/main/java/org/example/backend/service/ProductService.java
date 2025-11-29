package org.example.backend.service;

import org.example.backend.exceptions.WarehouseAppException;
import org.example.backend.model.entities.Product;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.WarehouseRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepo productRepo;


    public ProductService(ProductRepo productRepo, WarehouseRepo warehouseRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProductsById(String warehouseId) {
        return  productRepo.findByWarehouseId(warehouseId);
    }

    public Product createProduct(Product product) {
        Product newProduct = product.withBarcode(generateBarcode());
        return productRepo.save(newProduct);
    }

    public Product getProductById(String id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Product not found: " + id));
    }

    public Product updateProduct(String id, Product product) {
        Product oldData = productRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Product not found: " + id));
        productRepo.save(
                oldData.
                        withName(product.name()).
                        withBarcode(product.barcode()).
                        withQuantity(product.quantity()).
                        withDescription(product.description()));
        return  product;
    }

    public void deleteProduct(String id) {
        productRepo.deleteById(id);
    }

    private String generateBarcode() {
        return UUID.randomUUID().toString();
    }
}
