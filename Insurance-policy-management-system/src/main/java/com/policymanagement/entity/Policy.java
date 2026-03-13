package com.policymanagement.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policyId;

    @Column(unique = true, nullable = false)
    private String policyName;

    @Column(nullable = false)
    private double premiumAmount;

    @Column(nullable = false)
    private int durationMonths;

    @Column(nullable = false)
    private double coverageAmount;

    @Column(nullable = false)
    private String policyType;

    @Column(nullable = false)
    private boolean active;

    private LocalDateTime createdDate;
}