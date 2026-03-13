package com.policymanagement.service;

import java.util.List;
import com.policymanagement.entity.CustomerPolicy;

public interface CustomerPolicyService {
    CustomerPolicy purchasePolicy(int customerId, int policyId);
    List<CustomerPolicy> getMyPolicies(int customerId);
    List<CustomerPolicy> getAllPurchases();
}