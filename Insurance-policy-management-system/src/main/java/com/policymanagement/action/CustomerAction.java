package com.policymanagement.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.policymanagement.entity.Customer;
import com.policymanagement.service.CustomerService;

@Component
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

	@Autowired
	CustomerService service ;
	
	private Customer customer  = new Customer();
	
	public String execute() {
		addCustomer();
		return SUCCESS;
	}
	
	public String  addCustomer() {
		
	customer = service.addCustomer(customer);
	
	return SUCCESS;
	}

	@Override
	public Customer getModel() {
		
		return customer ;
	}
}
