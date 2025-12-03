package org.example.backend.repository;

import org.example.backend.model.entities.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends MongoRepository<AppUser,String> {
}
