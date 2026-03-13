package com.policymanagement.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.policymanagement.entity.Customer;
import com.policymanagement.service.CustomerService;

@Component("loginAction")
public class LoginAction extends ActionSupport implements ModelDriven<Customer> {

	private Customer customer = new Customer();
	private String role;

	@Autowired
	private CustomerService customerService;

	public String login() {
	    System.out.println("Attempting login for email: " + customer.getEmail());

	    if ("admin@insurance.com".equals(customer.getEmail()) && "admin123".equals(customer.getPassword())) {
	        System.out.println("Admin login successful.");
	        HttpSession session = ServletActionContext.getRequest().getSession();
	        session.setAttribute("email", customer.getEmail());
	        session.setAttribute("role", "AGENT");
	        this.role = "AGENT";
	        return "agent";
	    }

	    Customer dbCustomer = customerService.findByEmailAndPassword(customer.getEmail(), customer.getPassword());

	    if (dbCustomer != null) {
	        System.out.println("Login successful for email: " + dbCustomer.getEmail() + " with role: " + dbCustomer.getRole());
	        HttpSession session = ServletActionContext.getRequest().getSession();
	        session.setAttribute("email", dbCustomer.getEmail());
	        session.setAttribute("role", dbCustomer.getRole());
	        session.setAttribute("loggedInCustomer", dbCustomer);
	        this.role = dbCustomer.getRole();

	        if ("AGENT".equalsIgnoreCase(dbCustomer.getRole())) {
	            return "agent";
	        }
	        if ("CUSTOMER".equalsIgnoreCase(dbCustomer.getRole())) {
	            return "customer";
	        }
	    }

	    System.out.println("Login failed for email: " + customer.getEmail());
	    addActionError("Invalid email or password.");
	    return ERROR;
	}

	public String logout() {
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		if (session != null)
			session.invalidate();
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}