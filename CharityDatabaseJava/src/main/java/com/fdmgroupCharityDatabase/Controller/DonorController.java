package com.fdmgroupCharityDatabase.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroupCharityDatabase.Model.Donation;
import com.fdmgroupCharityDatabase.Model.Donor;
import com.fdmgroupCharityDatabase.Service.DonationService;
import com.fdmgroupCharityDatabase.Service.DonorService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;


@Controller
public class DonorController {
	
	private static Logger logger = LogManager.getLogger(DonorController.class);
	
	@Autowired
	private DonorService donorService;
	
	@Autowired
	private DonationService donationService;
		
	@GetMapping("/donor-add")
	public String goToDonorAdd(Model model, HttpSession session) {
		if (session.getAttribute("current_user") != null) {
			model.addAttribute("donor", new Donor());
			return "donor-add";
			}
		else {
			return "redirect:/login";
		}
	}

	@PostMapping("/donor-add")
	public String addDonor(@ModelAttribute("donor") Donor donor) {
	    donorService.saveDonor(donor);
	    Long id = donor.getDonorId();
	    logger.info("New Donor Added With ID : " + id);
	    return "redirect:/donor";
	}		

	@PostMapping("/donors/{id}/delete")
	public String deleteDonor(@PathVariable("id") Long donorId) {
	    donorService.deleteDonor(donorId);
	    logger.info("Donor ID : " + donorId + " Deleted");
	    return "redirect:/donors";
	}

	
	@GetMapping("/donors/{id}/update")
	public String goToDonorUpdate(@PathVariable("id") Long donorId, Model model, HttpSession session){
	    if (session.getAttribute("current_user") != null) {
	        Optional<Donor> optionalDonor = donorService.findDonorById(donorId);
	        if (optionalDonor.isPresent()) {
	            Donor donor = optionalDonor.get();
	            model.addAttribute("donor", donor);
	            return "donor-update";
	        } else {
	            throw new EntityNotFoundException("Donor not found with id: " + donorId);
	        }
	    } else {
	        return "redirect:/login";
	    }
	}

	
	@PostMapping("/donors/{id}/update")
	public String updateDonor(@PathVariable("id") Long donorId, @ModelAttribute("donor") Donor donor, Model model) {
	    System.out.println(donorId);
	    Donor updatedDonor = donorService.updateDonor(donorId, donor);
	    model.addAttribute("donor", updatedDonor);
	    logger.info("Donor With ID " + donorId + " Has Been Updated With New Fields.");
	    return "redirect:/donor";
	}

	
	@GetMapping("/donors")
	public String getDonorList(@RequestParam(value = "search", required = false) String search, Model model, HttpSession session) {
	    List<Donor> donorList;
	    if (session.getAttribute("current_user") != null) { 
	    	if(search != null && !search.isEmpty()) {
	    		donorList = donorService.searchDonors(search);
	    		logger.info("Display All Donors Searched By Word : " + search);
	    		} 
	    	else {
	    		donorList = donorService.findAllDonors();
	    		logger.info("Display All Donors");
	    		}
	    	
	    	model.addAttribute("donorlist", donorList);
	    	return "donor-list";
	    }
	    else {
	    	return "redirect:/login";
	    	}
	}

	
	@GetMapping("/search/donors")
	public String searchDonors(@RequestParam("search") String search, Model model) {
	    List<Donor> donors = donorService.searchDonors(search);
	    model.addAttribute("donorlist", donors);
	    return "donor-list";
	}
	
	@GetMapping("/donors/{id}")
	public String goToDonorProfile(@PathVariable("id") Long donorId, Model model, HttpSession session) {
	    if (session.getAttribute("current_user") == null) {
	        return "redirect:/login";
	    }
	    
	    return "donor-profile";
	}
	
	@GetMapping("/donors/{id}/donations")
	public String showDonations(@PathVariable("id") Long donorId, Model model, HttpSession session) {
	    if (session.getAttribute("current_user") == null) {
	        return "redirect:/login";
	    }

	    List<Donation> donations = donationService.findDonationsByDonorId(donorId);
	    if (donations.isEmpty()) {
	        model.addAttribute("error", "No records found for donor ID: " + donorId);
	        return "record-not-found";
	    }
	    
	    model.addAttribute("donations", donations);
	    model.addAttribute("id", donorId);
	    logger.info("Dispaly Donations Made By Donor ID: " + donorId);
	    return "donor-donations";
	}
}
