package com.policymanagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.policymanagement.dao.CustomerDAO;
import com.policymanagement.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public Customer addCustomer(Customer customer) {
        LocalDate dob = LocalDate.parse(customer.getDateOfBirth());
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("Customer must be 18 years or older.");
        }
        customer.setRegisteredDate(LocalDateTime.now());
        customer.setRole("CUSTOMER");
        return customerDAO.saveCustomer(customer);
    }

    @Override
    public Customer findByEmailAndPassword(String email, String password) {
        return customerDAO.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerDAO.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(long phoneNumber) {
        return customerDAO.existsByPhone(phoneNumber);
    }
}