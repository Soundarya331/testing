package com.policymanagement.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
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

	// Changed to Map<String, Object> to support List<Policy> as value
	private Map<String, Object> jsonResponse = new HashMap<>();

	@Override
	public void validate() {
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
	@SkipValidation
	@Override
	public String execute() {
	    return SUCCESS;
	}
	public String addPolicy() {
		try {
			policyService.addPolicy(policy);
			return SUCCESS;
		} catch (IllegalArgumentException e) {
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	@SkipValidation
	public String listPolicies() {
		policyList = policyService.getAllPolicies();
		if (policyList == null || policyList.isEmpty()) {
			policyList = new ArrayList<>();
			jsonResponse.put("status", "empty");
			jsonResponse.put("message", "No policies available. Click Create Policy to add one.");
		} else {
			jsonResponse.put("status", "success");
		}
		jsonResponse.put("policyList", policyList);
		return SUCCESS;
	}

	@SkipValidation
	public String deletePolicy() {
		try {
			policyService.deletePolicy(policyId);
			jsonResponse.put("status", "success");
			jsonResponse.put("message", "Policy deleted successfully.");
			return SUCCESS;
		} catch (IllegalArgumentException e) {
			jsonResponse.put("status", "error");
			jsonResponse.put("message", e.getMessage());
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

	public Map<String, Object> getJsonResponse() {
		return jsonResponse;
	}
}