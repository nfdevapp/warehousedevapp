package org.example.backend.controller;

import org.example.backend.model.dto.ProductDto;
import org.example.backend.model.entities.Product;
import org.example.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/warehouse/{warehouseId}")
    public List<Product> getAllProductsById(@PathVariable String warehouseId) {
        return  productService.getAllProductsById(warehouseId);
    }
    @PostMapping
    public Product createProduct(@RequestBody ProductDto product) {
        return productService.createProduct(product);
    }
    @GetMapping("/{id}")
    public ProductDto getPoductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable String id, @RequestBody ProductDto product) {
        return  productService.updateProduct(id, product);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
