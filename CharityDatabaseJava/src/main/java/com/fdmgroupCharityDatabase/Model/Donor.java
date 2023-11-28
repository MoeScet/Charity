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
public class Donor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long donorId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	
	@OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();

	
	public Donor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Donor(String firstName, String lastName, String email, String mobile) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	
	
	public long getDonorId() {
		return donorId;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}
}