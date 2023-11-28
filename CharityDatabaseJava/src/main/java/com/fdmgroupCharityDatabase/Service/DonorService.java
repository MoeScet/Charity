package com.fdmgroupCharityDatabase.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroupCharityDatabase.Model.Donation;
import com.fdmgroupCharityDatabase.Model.Donor;
import com.fdmgroupCharityDatabase.Repository.DonationRepository;
import com.fdmgroupCharityDatabase.Repository.DonorRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class DonorService {
	
	@Autowired
	private DonorRepository donorRepo;
		
	public List<Donor> findAllDonors() {
		return donorRepo.findAll();		
	}
	
	public Optional<Donor> findDonorById (Long donorId) {
		return donorRepo.findById(donorId);
	}
	
	public void saveDonor(Donor donor) {
		donorRepo.save(donor);
	}

	public Donor updateDonor(Long donorId, Donor donor) {
	    Optional<Donor> optionalDonor = donorRepo.findById(donorId);
	    if (optionalDonor.isPresent()) {
	        Donor existingDonor = optionalDonor.get();
	        
	        if (donor.getFirstName().equals("") == false) {
	            existingDonor.setFirstName(donor.getFirstName());
	        }
	        if (donor.getLastName().equals("") == false) {
	            existingDonor.setLastName(donor.getLastName());
	        }
	        if (donor.getEmail().equals("") == false) {
	            existingDonor.setEmail(donor.getEmail());
	        }
	        if (donor.getMobile().equals("") == false) {
	            existingDonor.setMobile(donor.getMobile());
	        }
	        
	        return donorRepo.save(existingDonor);
	    } else {
	        throw new EntityNotFoundException("Donor not found with id: " + donorId);
	    }
	}

    public void deleteDonor(Long id) {
        Optional<Donor> optionalDonor = donorRepo.findById(id);
        if (optionalDonor.isPresent()) {
            donorRepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Donor not found with id: " + id);
        }
    }
    
    public List<Donor> searchDonors(String search) {
        return donorRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, search);
    }
    
}
