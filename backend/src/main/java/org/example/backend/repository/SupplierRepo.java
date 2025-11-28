package org.example.backend.repository;

import org.example.backend.model.entities.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepo extends MongoRepository<Supplier,String> {
}
