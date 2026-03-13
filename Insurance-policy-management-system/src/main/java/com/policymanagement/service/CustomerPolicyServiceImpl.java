package com.policymanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.policymanagement.dao.CustomerDAO;
import com.policymanagement.dao.CustomerPolicyDAO;
import com.policymanagement.dao.PolicyDAO;
import com.policymanagement.entity.Customer;
import com.policymanagement.entity.CustomerPolicy;
import com.policymanagement.entity.Policy;

@Service
public class CustomerPolicyServiceImpl implements CustomerPolicyService {

    @Autowired
    private CustomerPolicyDAO cpDAO;

    @Autowired
    private PolicyDAO policyDAO;

    @Override
    public CustomerPolicy purchasePolicy(int customerId, int policyId) {
        if (cpDAO.alreadyPurchased(customerId, policyId)) {
            throw new IllegalArgumentException("You have already purchased this policy.");
        }
        Policy policy = policyDAO.getPolicyById(policyId);
        Customer c = new Customer();
        c.setCustomerId(customerId);
        CustomerPolicy cp = new CustomerPolicy();
        cp.setCustomer(c);
        cp.setPolicy(policy);
        cp.setPurchaseDate(LocalDateTime.now());
        cp.setTotalPremiumPaid(policy.getPremiumAmount());
        cp.setStatus("ACTIVE");
        return cpDAO.save(cp);
    }

    @Override
    public List<CustomerPolicy> getMyPolicies(int customerId) {
        return cpDAO.getByCustomer(customerId);
    }

    @Override
    public List<CustomerPolicy> getAllPurchases() {
        return cpDAO.getAll();
    }
}