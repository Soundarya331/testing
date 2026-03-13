package com.policymanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.policymanagement.dao.PolicyDAO;
import com.policymanagement.entity.Policy;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyDAO policyDAO;

    @Override
    public Policy addPolicy(Policy policy) {
        policy.setActive(true);
        policy.setCreatedDate(LocalDateTime.now());
        return policyDAO.savePolicy(policy);
    }
    @Override
    public List<Policy> getAllPolicies() {
        return policyDAO.getAllPolicies();
    }

    @Override
    public boolean deletePolicy(int policyId) {
        if (policyDAO.isPolicyPurchased(policyId)) {
            throw new IllegalArgumentException("Cannot delete a purchased policy.");
        }
        return policyDAO.deletePolicy(policyId);
    }
}