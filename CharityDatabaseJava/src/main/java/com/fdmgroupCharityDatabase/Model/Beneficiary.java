package com.fdmgroupCharityDatabase.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Beneficiary {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long beneficiaryId;
	
	private String name;
	private String email;
	
	@OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();
	
	public Beneficiary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Beneficiary(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public long getBeneficiaryId() {
		return beneficiaryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}
	
	
	
}
