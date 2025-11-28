package org.example.backend.repository;

import org.example.backend.model.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends MongoRepository<Product,String> {
    List<Product> findByWarehouseId(String warehouseId);
}
