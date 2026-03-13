package com.policymanagement.service;

import org.springframework.stereotype.Service;

import com.policymanagement.entity.Customer;

@Service
public interface CustomerService {
    Customer addCustomer(Customer customer);
    Customer findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    boolean existsByPhone(long phoneNumber);
	
}