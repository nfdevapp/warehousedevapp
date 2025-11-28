package org.example.backend.service;

import org.example.backend.repository.CustomerRepo;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    public CustomerService(CustomerRepo customerRepo, CustomerRepo customerRepo1) {
        this.customerRepo = customerRepo1;
    }
}
