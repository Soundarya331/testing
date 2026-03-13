package com.policymanagement.action;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.policymanagement.entity.Customer;
import com.policymanagement.service.CustomerService;

@Component("registrationAction")
public class RegistrationAction extends ActionSupport implements ModelDriven<Customer> {

	private Customer customer = new Customer();

	@Autowired
	private CustomerService customerService;

	@Override
	public void validate() {
		if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
			addFieldError("customerName", "Name is required.");
		}
		if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
			addFieldError("email", "Email is required.");
		} else if (!customer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			addFieldError("email", "Invalid email format.");
		} else if (customerService.existsByEmail(customer.getEmail())) {
			addFieldError("email", "Email already registered.");
		}
		if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
			addFieldError("password", "Password is required.");
		} else if (customer.getPassword().length() < 6) {
			addFieldError("password", "Password must be at least 6 characters.");
		}
		if (String.valueOf(customer.getPhoneNumber()).length() != 10) {
			addFieldError("phoneNumber", "Phone must be 10 digits.");
		} else if (customerService.existsByPhone(customer.getPhoneNumber())) {
			addFieldError("phoneNumber", "Phone already registered.");
		}
		if (customer.getDateOfBirth() == null || customer.getDateOfBirth().trim().isEmpty()) {
			addFieldError("dateOfBirth", "Date of birth is required.");
		} else {
			try {
				LocalDate dob = LocalDate.parse(customer.getDateOfBirth());
				if (Period.between(dob, LocalDate.now()).getYears() < 18) {
					addFieldError("dateOfBirth", "Must be 18 years or older.");
				}
			} catch (Exception e) {
				addFieldError("dateOfBirth", "Invalid format. Use yyyy-MM-dd.");
			}
		}
	}

	public String register() {
		customer = customerService.addCustomer(customer);
		return SUCCESS;
	}

	@Override
	public Customer getModel() {
		return customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}