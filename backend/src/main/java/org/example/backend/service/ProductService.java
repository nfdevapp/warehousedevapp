package org.example.backend.service;

import org.example.backend.exceptions.WarehouseAppException;
import org.example.backend.model.dto.ProductDto;
import org.example.backend.model.entities.Customer;
import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Supplier;
import org.example.backend.repository.CustomerRepo;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.SupplierRepo;
import org.example.backend.repository.WarehouseRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final SupplierRepo supplierRepo;
    private final CustomerRepo  customerRepo;


    public ProductService(ProductRepo productRepo, WarehouseRepo warehouseRepo, SupplierRepo supplierRepo, CustomerRepo customerRepo) {
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
        this.customerRepo = customerRepo;
    }

    public List<Product> getAllProductsById(String warehouseId) {
        return  productRepo.findByWarehouseId(warehouseId);
    }

    public Product createProduct(ProductDto productDto) {
        Supplier newSupplier = Supplier.builder()
                .name(productDto.supplierName())
                .city(productDto.supplierCity())
                .street(productDto.supplierStreet())
                .houseNumber(productDto.supplierHouseNumber())
                .zipCode(productDto.supplierZipCode())
                .build();
        supplierRepo.save(newSupplier);
        Customer newCustomer = Customer.builder()
                .name(productDto.customerName())
                .city(productDto.customerCity())
                .street(productDto.customerStreet())
                .houseNumber(productDto.customerHouseNumber())
                .zipCode(productDto.customerZipCode())
                .build();
        customerRepo.save(newCustomer);
        Product newProduct = Product.builder()
                .name(productDto.name())
                .barcode(generateBarcode())
                .description(productDto.description())
                .quantity(productDto.quantity())
                .customerId(newCustomer.id())
                .supplierId(newSupplier.id())
                .warehouseId(productDto.warehouseId())
                .build();
        return  productRepo.save(newProduct);
    }

    public ProductDto getProductById(String id) {
        Product product = getproduct(id);
        Customer customer = getcustomer(product.customerId());
        Supplier supplier = getsupplier(product.supplierId());

        return new ProductDto(
                product.name(),
                product.barcode(),
                product.description(),
                product.quantity(),

                customer.name(),
                customer.city(),
                customer.street(),
                customer.houseNumber(),
                customer.zipCode() ,

                supplier.name(),
                supplier.city(),
                supplier.street(),
                supplier.houseNumber(),
                supplier.zipCode(),

                product.warehouseId());
    }

    public ProductDto updateProduct(String id, ProductDto dto) {
        Product oldProduct = getproduct(id);
        Customer oldCustomer = getcustomer(oldProduct.customerId());
        Supplier oldSupplier = getsupplier(oldProduct.supplierId());

        Product updatedProduct = oldProduct
                .withName(dto.name())
                .withBarcode(dto.barcode())
                .withQuantity(dto.quantity())
                .withDescription(dto.description());

        Customer updatedCustomer = oldCustomer
                .withName(dto.customerName())
                .withCity(dto.customerCity())
                .withStreet(dto.customerStreet())
                .withHouseNumber(dto.customerHouseNumber())
                .withZipCode(dto.customerZipCode());

        Supplier updatedSupplier = oldSupplier
                .withName(dto.supplierName())
                .withCity(dto.supplierCity())
                .withStreet(dto.supplierStreet())
                .withHouseNumber(dto.supplierHouseNumber())
                .withZipCode(dto.supplierZipCode());

        productRepo.save(updatedProduct);
        customerRepo.save(updatedCustomer);
        supplierRepo.save(updatedSupplier);

        return dto;
    }


    public void deleteProduct(String id) {
        Product product = getproduct(id);
        Customer customer = getcustomer(product.customerId());
        Supplier supplier = getsupplier(product.supplierId());
        customerRepo.delete(customer);
        supplierRepo.delete(supplier);
        productRepo.deleteById(id);
    }

    private Product getproduct(String id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Product not found: " + id));
    }

    private Customer getcustomer(String id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Customer not found: " + id));
    }

    private Supplier getsupplier(String id) {
        return supplierRepo.findById(id)
                .orElseThrow(() -> new WarehouseAppException("Supplier not found: " + id));
    }

    private String generateBarcode() {
        return UUID.randomUUID().toString();
    }
}
