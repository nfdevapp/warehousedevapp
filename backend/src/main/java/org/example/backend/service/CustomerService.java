package org.example.backend.service;

import org.example.backend.model.entities.Customer;
import org.example.backend.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    public CustomerService(CustomerRepo customerRepo, CustomerRepo customerRepo1) {
        this.customerRepo = customerRepo1;
    }
    public List<Customer> getAllCustomers() {
        return  customerRepo.findAll();

    }
}
