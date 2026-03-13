package com.policymanagement.service;

import java.util.List;
import com.policymanagement.entity.Policy;

public interface PolicyService {
    Policy addPolicy(Policy policy);
    List<Policy> getAllPolicies();
    boolean deletePolicy(int policyId);
}
