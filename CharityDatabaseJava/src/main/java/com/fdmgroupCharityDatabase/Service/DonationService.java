package com.fdmgroupCharityDatabase.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroupCharityDatabase.Model.Donation;
import com.fdmgroupCharityDatabase.Repository.DonationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DonationService {
	@Autowired
	private DonationRepository donationRepo;
	
	public List<Donation> findAllDonations() {
		return donationRepo.findAll();		
	}
	
	public void saveDonation(Donation donation) {
		donationRepo.save(donation);
	}
	
	public Donation updateDonation(Long donationId, Donation donation) {
	    Optional<Donation> optionalDonation = donationRepo.findById(donationId);
	    if (optionalDonation.isPresent()) {
	    	Donation existingDonation = optionalDonation.get();
	        
	        if (donation.getDonor() != null) {
	        	existingDonation.setDonor(donation.getDonor());
	        }	
	        
	        if (donation.getDonor() != null) {
	        	existingDonation.setBeneficiary(donation.getBeneficiary());
	        }	
	        
	        if (donation.getAmount() !=  null) {
	        	existingDonation.setAmount(donation.getAmount());
	        }
	        
	        if (donation.getType() != null) {
	        	existingDonation.setType(donation.getType());
	        }
	        
	        if (donation.getDate() != null) {
	        	existingDonation.setDate(donation.getDate());
	        }
	        
	        return donationRepo.save(existingDonation);
	    } else {
	        throw new EntityNotFoundException("Donation record not found with id: " + donationId);
	    }
	}
	
	 public void deleteDonation(Long id) {
	        Optional<Donation> optionalDonation = donationRepo.findById(id);
	        if (optionalDonation.isPresent()) {
	        	donationRepo.deleteById(id);
	        } else {
	            throw new EntityNotFoundException("Donation record not found with id: " + id);
	        }
	    }
	 
	 public List<Donation> findDonationsByDonorId(long donorId) {
		    return donationRepo.findByDonorDonorId(donorId);
		}
	 
	 public List<Donation> findDonationsByBeneficiaryId(long beneficiaryId) {
		    return donationRepo.findByBeneficiaryBeneficiaryId(beneficiaryId);
		}
	 
	 public List<Donation> getDonationsByDateRange(LocalDate startDate, LocalDate endDate) {
	        return donationRepo.findByDateBetween(startDate, endDate);
	    }

	public Optional<Donation> findDonationById(Long donationId) {
		return donationRepo.findById(donationId);
	}
}
