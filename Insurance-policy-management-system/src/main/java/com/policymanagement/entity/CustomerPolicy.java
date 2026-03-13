package com.policymanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import lombok.Data;


@Data
@Entity
@Table(name="purchases")
public class CustomerPolicy {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@ManyToOne
    @JoinColumn(name = "policyId")
	private Policy policy;
	
	private LocalDateTime purchaseDate;
	
	private double totalPremiumPaid;
	
	private String status;

}
