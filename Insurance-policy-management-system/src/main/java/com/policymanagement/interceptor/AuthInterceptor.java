package com.policymanagement.interceptor;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {

    private String requiredRole;

    public void setRequiredRole(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        HttpSession session = ServletActionContext.getRequest().getSession(false);

        if (session == null) {
            return "unauthorized";
        }

        String role = (String) session.getAttribute("role");

        if (role == null || !role.equalsIgnoreCase(requiredRole)) {
            return "unauthorized";
        }

        return invocation.invoke();
    }
}