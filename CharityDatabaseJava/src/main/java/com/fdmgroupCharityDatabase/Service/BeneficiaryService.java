package com.fdmgroupCharityDatabase.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroupCharityDatabase.Model.Beneficiary;
import com.fdmgroupCharityDatabase.Model.Donor;
import com.fdmgroupCharityDatabase.Repository.BeneficiaryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BeneficiaryService {
	@Autowired
	private BeneficiaryRepository benefRepo;
	
	public List<Beneficiary> findAllBeneficiaries() {
		return benefRepo.findAll();		
	}
	
	public void saveBeneficiary(Beneficiary beneficiary) {
		benefRepo.save(beneficiary);
	}
	
	public Optional<Beneficiary> findBeneficiaryById(Long beneficiaryId) {
		return benefRepo.findById(beneficiaryId);
	}

	public Beneficiary updateBeneficiary(Long donorId, Beneficiary beneficiary) {
	    Optional<Beneficiary> optionalBeneficiary = benefRepo.findById(donorId);
	    if (optionalBeneficiary.isPresent()) {
	    	Beneficiary existingBeneficiary = optionalBeneficiary.get();
	        
	        if (beneficiary.getName().equals("") == false) {
	            existingBeneficiary.setName(beneficiary.getName());
	        }
	       
	        if (beneficiary.getEmail().equals("") == false) {
	        	existingBeneficiary.setEmail(beneficiary.getEmail());
	        }	        
	        return benefRepo.save(existingBeneficiary);
	    } else {
	        throw new EntityNotFoundException("Beneficiary not found with id: " + donorId);
	    }
	}


    public void deleteBeneficiary(Long id) {
        Optional<Beneficiary> optionalBeneficiary = benefRepo.findById(id);
        if (optionalBeneficiary.isPresent()) {
        	benefRepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Beneficiary not found with id: " + id);
        }
    }
    
    public List<Beneficiary> searchBeneficiaries(String search) {
        return benefRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
    }
}
