package org.example.backend.repository;

import org.example.backend.model.entities.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepo extends MongoRepository <Warehouse,String> {
}
