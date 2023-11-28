package com.fdmgroupCharityDatabase.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    @ManyToOne
    @JoinColumn(name = "donor_id", referencedColumnName = "donorId")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "beneficiaryId")
    private Beneficiary beneficiary;
    
    @ManyToOne
    @JoinColumn(name = "agent_id", referencedColumnName = "agentId")
    private Agent agent;

    public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "Type")
    private String type;

    public Donation() {
    }

    public Donation(Donor donor, Beneficiary beneficiary, Double amount, LocalDate date,String type) {
        this.donor = donor;
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public Long getDonationId() {
        return donationId;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
