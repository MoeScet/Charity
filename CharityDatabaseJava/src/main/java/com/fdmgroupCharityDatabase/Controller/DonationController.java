package com.fdmgroupCharityDatabase.Controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroupCharityDatabase.Model.Agent;
import com.fdmgroupCharityDatabase.Model.Beneficiary;
import com.fdmgroupCharityDatabase.Model.Donation;
import com.fdmgroupCharityDatabase.Model.Donor;
import com.fdmgroupCharityDatabase.Service.AgentService;
import com.fdmgroupCharityDatabase.Service.BeneficiaryService;
import com.fdmgroupCharityDatabase.Service.DonationService;
import com.fdmgroupCharityDatabase.Service.DonorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DonationController {
	
	private static Logger logger = LogManager.getLogger(DonationController.class);
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private DonorService donorService;
	
	@Autowired
	private BeneficiaryService beneficiaryService;
	
	@Autowired
	private DonationService donationService;
	
	@GetMapping("/donation-add")
	public String goToDonationAdd(Model model, HttpSession session){
		if (session.getAttribute("current_user") != null) {
			List<Donor> donorList = donorService.findAllDonors();
		    List<Beneficiary> beneficiaryList = beneficiaryService.findAllBeneficiaries();
		    model.addAttribute("donorlist", donorList);
		    model.addAttribute("beneficiarylist", beneficiaryList);
		    
		    String currentAgentId = (String) session.getAttribute("current_user");
		    Agent currentAgent = agentService.findAgent(currentAgentId);
		    model.addAttribute("currentAgent", currentAgent);
		    return "donation-add";
		}
		else {
			return "redirect:/login";
		}
	    
	}
	
	@PostMapping("/donation-add")
	public String addDonation(@ModelAttribute("donation") Donation donation, HttpSession session) {
		if (session.getAttribute("current_user") != null) {
			String currentAgentId = (String) session.getAttribute("current_user");
			Agent currentAgent = agentService.findAgent(currentAgentId);
			donation.setAgent(currentAgent);
		    donationService.saveDonation(donation);
		    Long id = donation.getDonationId();
		    logger.info("New Donation Added With ID: " + id);
		    return "redirect:/donation";
		}
		else {
			return "redirect:/login";
		}
		
	}	
	
	@GetMapping("/donations")
	public String getAllDonations(Model model, HttpSession session) {
		if (session.getAttribute("current_user") != null) {
			List<Donation> donations = donationService.findAllDonations();
			model.addAttribute("donationlist",donations);
			logger.info("Display All Donation Records");
			return "donation-list";
			}
		else {
			return "redirect:/login";
			}
	}
	
	@GetMapping("/donation-update")
	public String goToDonationUpdate(Model model,HttpSession session){
		if (session.getAttribute("current_user") != null) {
			List<Donor> donorList = donorService.findAllDonors();
		    List<Beneficiary> beneficiaryList = beneficiaryService.findAllBeneficiaries();
		    model.addAttribute("donorlist", donorList);
		    model.addAttribute("beneficiarylist", beneficiaryList);
		    
		    String currentAgentId = (String) session.getAttribute("current_user");
		    Agent currentAgent = agentService.findAgent(currentAgentId);
		    model.addAttribute("currentAgent", currentAgent);
			return "donation-update";
		}
		else {
			return "redirect:/login";
			}
	}
	
	@PostMapping("/donation-update")
	public String updateDonation(@RequestParam Long donationId, Donation donation, Model model, HttpSession session) {
		List<Donor> donorList = donorService.findAllDonors();
	    List<Beneficiary> beneficiaryList = beneficiaryService.findAllBeneficiaries();
	    model.addAttribute("donorlist", donorList);
	    model.addAttribute("beneficiarylist", beneficiaryList);
	    
	    String currentAgentId = (String) session.getAttribute("current_user");
	    Agent currentAgent = agentService.findAgent(currentAgentId);
	    model.addAttribute("currentAgent", currentAgent);
		Donation updatedDonation= donationService.updateDonation(donationId, donation);
	    model.addAttribute("donation", updatedDonation);
	    logger.info("Donation With ID " + donationId + " Has Been Updated With New Fields.");
	    return "redirect:/donation";
	}
	
	@GetMapping("/donation-delete")
	public String goToDonationDelete(HttpSession session){
		if (session.getAttribute("current_user") != null) {
		return "donation-delete";
		}
		else {
			return "redirect:/login";
		}
	}
	
	@PostMapping("/donation-delete")
	public String deleteDonation(@RequestParam Long donationId) {
		donationService.deleteDonation(donationId);
		logger.info("Donation With ID " + donationId + " Has Been Deleted.");
	    return "redirect:/donation";
	}
	
	@GetMapping("/donations/date-range")
	public String getDonationsByDateRange(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
	                                       @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate,
	                                       Model model, HttpSession session) {
		 if (session.getAttribute("current_user") != null) {
			 List<Donation> donationlist = donationService.getDonationsByDateRange(startDate, endDate);
			    model.addAttribute("donationlist", donationlist);
			    logger.info("Display Donations Between " + startDate + " and " + endDate);
			    return "donation-list";
		 }
		 else {
			 return "redirect:/login";
		 }
	}

}
