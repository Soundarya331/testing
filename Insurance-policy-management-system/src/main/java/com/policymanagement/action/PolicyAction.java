package com.policymanagement.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.policymanagement.entity.Policy;
import com.policymanagement.service.PolicyService;

@Component("policyAction")
public class PolicyAction extends ActionSupport implements ModelDriven<Policy> {

	private Policy policy = new Policy();
	private List<Policy> policyList;
	private int policyId;

	@Autowired
	private PolicyService policyService;

	@Override
	public void validate() {
		System.out.println("=== validate called ===");
		System.out.println("Policy Name: " + policy.getPolicyName());
		System.out.println("Premium: " + policy.getPremiumAmount());
		System.out.println("Coverage: " + policy.getCoverageAmount());
		System.out.println("Duration: " + policy.getDurationMonths());
		System.out.println("Type: " + policy.getPolicyType());

		if (policy.getPolicyName() == null || policy.getPolicyName().trim().isEmpty()) {
			addFieldError("policyName", "Policy name is required.");
		}
		if (policy.getPremiumAmount() <= 0) {
			addFieldError("premiumAmount", "Premium must be greater than zero.");
		}
		if (policy.getCoverageAmount() <= 0) {
			addFieldError("coverageAmount", "Coverage amount is required.");
		} else if (policy.getCoverageAmount() <= policy.getPremiumAmount()) {
			addFieldError("coverageAmount", "Coverage must be greater than premium.");
		}
		if (policy.getDurationMonths() < 12) {
			addFieldError("durationMonths", "Duration must be at least 12 months.");
		}
		if (policy.getPolicyType() == null || policy.getPolicyType().trim().isEmpty()) {
			addFieldError("policyType", "Policy type is required.");
		}
	}

	public String addPolicy() {
		System.out.println("=== addPolicy called ===");
		try {
			policyService.addPolicy(policy);
			return SUCCESS;
		} catch (IllegalArgumentException e) {
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	public String listPolicies() {
		policyList = policyService.getAllPolicies();
		return SUCCESS;
	}

	public String deletePolicy() {
		try {
			policyService.deletePolicy(policyId);
			return SUCCESS;
		} catch (IllegalArgumentException e) {
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	@Override
	public Policy getModel() {
		return policy;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public List<Policy> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<Policy> l) {
		this.policyList = l;
	}

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int id) {
		this.policyId = id;
	}
}