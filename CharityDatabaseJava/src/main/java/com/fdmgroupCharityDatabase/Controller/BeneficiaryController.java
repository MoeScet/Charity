package com.fdmgroupCharityDatabase.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroupCharityDatabase.Model.Beneficiary;
import com.fdmgroupCharityDatabase.Model.Donation;
import com.fdmgroupCharityDatabase.Model.Donor;
import com.fdmgroupCharityDatabase.Service.BeneficiaryService;
import com.fdmgroupCharityDatabase.Service.DonationService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Controller
public class BeneficiaryController {
	
	private static Logger logger = LogManager.getLogger(BeneficiaryController.class);
	
	@Autowired
	private BeneficiaryService benefService;
	
	@Autowired
	private DonationService donationService;
	
	@GetMapping("/beneficiary-add")
	public String goToBeneficiaryAdd(Model model, HttpSession session) {
		if (session.getAttribute("current_user") != null) {
			model.addAttribute("beneficiary", new Beneficiary());
			return "beneficiary-add";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@PostMapping("/beneficiary-add")
	public String addBeneficiary(@ModelAttribute("beneficiary") Beneficiary beneficiary, HttpSession session) {
		if (session.getAttribute("current_user") != null) {
			benefService.saveBeneficiary(beneficiary);
		    Long id = beneficiary.getBeneficiaryId();
			logger.info("New Beneficiary Added With ID: " + id);
		    return "redirect:/beneficiary";
		}
		else {
			return "redirect:/login";
		}
	}	
	
	@GetMapping("/beneficiaries/{id}")
	public String goToBeneficiaryProfile(@PathVariable("id") Long beneficiaryId, Model model, HttpSession session) {
	    if (session.getAttribute("current_user") == null) {
	        return "redirect:/login";
	    }
	    
	    return "beneficiary-profile";
	}
	
	@GetMapping("/beneficiaries/{id}/donations")
	public String showDonations(@PathVariable("id") Long beneficiaryId, Model model, HttpSession session) {
	    if (session.getAttribute("current_user") == null) {
	        return "redirect:/login";
	    }

	    List<Donation> donations = donationService.findDonationsByBeneficiaryId(beneficiaryId);
	    if (donations.isEmpty()) {
	        model.addAttribute("error", "No records found for beneficiary ID: " + beneficiaryId);
	        return "record-not-found";
	    }
	    
	    model.addAttribute("donations", donations);
	    model.addAttribute("id", beneficiaryId);
	    logger.info("Dispaly Donations Received By Beneficiary ID: " + beneficiaryId);
	    return "beneficiary-donations";
	}
	
	@PostMapping("/beneficiaries/{id}/delete")
	public String deleteBeneficiary(@PathVariable("id") Long beneficiaryId) {
	    benefService.deleteBeneficiary(beneficiaryId);
	    logger.info("New Beneficiary With ID " + beneficiaryId + " Deleted.");
	    return "redirect:/beneficiaries";
	}
	
	@GetMapping("/beneficiaries/{id}/update")
	public String goToBeneficiaryUpdate(@PathVariable("id") Long beneficiaryId, Model model, HttpSession session){
	    if (session.getAttribute("current_user") != null) {
	        Optional<Beneficiary> optionalBeneficiary = benefService.findBeneficiaryById(beneficiaryId);
	        if (optionalBeneficiary.isPresent()) {
	            Beneficiary beneficiary = optionalBeneficiary.get();
	            model.addAttribute("beneficiary", beneficiary);
	            return "beneficiary-update";
	        } else {
	            throw new EntityNotFoundException("Beneficiary not found with id: " + beneficiaryId);
	        }
	    } else {
	        return "redirect:/login";
	    }
	}

	
	@PostMapping("/beneficiaries/{id}/update")
	public String updateBeneficiary(@PathVariable("id") Long beneficiaryId, @ModelAttribute("beneficiary") Beneficiary beneficiary, Model model) {
	    
		Beneficiary updatedBeneficiary = benefService.updateBeneficiary(beneficiaryId, beneficiary);
	    model.addAttribute("beneficiary", updatedBeneficiary);
	    logger.info("Beneficiary With ID " + beneficiaryId + " Has Been Updated With New Fields.");
	    return "redirect:/beneficiary";
	}	
	
	@GetMapping("/beneficiaries")
	public String getBeneficiaryList(@RequestParam(value = "search", required = false) String search, Model model, HttpSession session) {
	    List<Beneficiary> beneficiaryList;
	    if (session.getAttribute("current_user") != null) { 
	    	if(search != null && !search.isEmpty()) {
	    		beneficiaryList = benefService.searchBeneficiaries(search);
	    		logger.info("Display All Beneficiaries Searched By Word : " + search);
	    		} 
	    	else {
	    		beneficiaryList = benefService.findAllBeneficiaries();
	    		logger.info("Display All Beneficiaries");
	    		}

	    	model.addAttribute("beneficiarylist", beneficiaryList);
	    	return "beneficiary-list";
	    }
	    else {
	    	return "redirect:/login";
	    	}
	}

	
	@GetMapping("/search/beneficiaries")
	public String searchBeneficiaries(@RequestParam("search") String search, Model model) {
	    List<Beneficiary> beneficiaries = benefService.searchBeneficiaries(search);
	    model.addAttribute("beneficiaylist", beneficiaries);
	    return "beneficiary-list";
	}	
}
