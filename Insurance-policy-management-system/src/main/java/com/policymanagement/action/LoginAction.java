package com.policymanagement.action;

import java.util.HashMap;
import java.util.Map;

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
    
 // ADD these two fields
    private String email;
    private String password;

    // ADD getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * jsonResponse is exposed as the root object serialized to JSON.
     * Fields:
     *   status  : "success" | "error"
     *   role    : "AGENT" | "CUSTOMER"
     *   redirect: URL the client should navigate to
     *   message : error message when status = "error"
     */
    private Map<String, String> jsonResponse = new HashMap<>();

    @Autowired
    private CustomerService customerService;

    // ------------------------------------------------------------------ //
    //  LOGIN                                                               //
    // ------------------------------------------------------------------ //
    public String login() {

        if (email != null) customer.setEmail(email);
        if (password != null) customer.setPassword(password);

        System.out.println("Email: " + customer.getEmail());
        System.out.println("Password: " + customer.getPassword());

        // ADMIN — hardcoded, no DB
        if ("admin@insurance.com".equals(customer.getEmail())
                && "admin123".equals(customer.getPassword())) {

            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("email", customer.getEmail());
            session.setAttribute("role", "AGENT");

            jsonResponse.put("status",   "success");
            jsonResponse.put("role",     "AGENT");
            jsonResponse.put("redirect", "agentDashboard");
            return "agent";
        }

        // CUSTOMER — check DB
        Customer dbCustomer = customerService.findByEmailAndPassword(
                customer.getEmail(), customer.getPassword());

        if (dbCustomer != null) {
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("email",            dbCustomer.getEmail());
            session.setAttribute("role",             dbCustomer.getRole());
            session.setAttribute("loggedInCustomer", dbCustomer);

            if ("AGENT".equalsIgnoreCase(dbCustomer.getRole())) {
                jsonResponse.put("status",   "success");
                jsonResponse.put("role",     "AGENT");
                jsonResponse.put("redirect", "agentDashboard");
                return "agent";
            }
            if ("CUSTOMER".equalsIgnoreCase(dbCustomer.getRole())) {
                jsonResponse.put("status",   "success");
                jsonResponse.put("role",     "CUSTOMER");
                jsonResponse.put("redirect", "customerDashboard");
                return "customer";
            }
        }

        // FAILED
        jsonResponse.put("status",  "error");
        jsonResponse.put("message", "Invalid email or password. Please try again.");
        return ERROR;
    }

    // ------------------------------------------------------------------ //
    //  LOGOUT                                                              //
    // ------------------------------------------------------------------ //
    public String logout() {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        if (session != null) session.invalidate();
        return SUCCESS;
    }

    // ------------------------------------------------------------------ //
    //  ModelDriven                                                         //
    // ------------------------------------------------------------------ //
    @Override
    public Customer getModel() { return customer; }

    // ------------------------------------------------------------------ //
    //  Getters / Setters                                                   //
    // ------------------------------------------------------------------ //
    public Customer getCustomer()              { return customer; }
    public void     setCustomer(Customer c)    { this.customer = c; }

    public String   getRole()                  { return role; }
    public void     setRole(String role)       { this.role = role; }

    /** Exposed as JSON root (configured in struts.xml via <param name="root">jsonResponse</param>) */
    public Map<String, String> getJsonResponse() { return jsonResponse; }
}