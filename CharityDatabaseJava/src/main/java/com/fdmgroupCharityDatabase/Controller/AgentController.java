package com.fdmgroupCharityDatabase.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import jakarta.servlet.http.HttpSession;

@Controller
public class AgentController {
	private static Logger logger = LogManager.getLogger(AgentController.class);
	
	//@Autowired
	//private AgentService agentService;
	
	@GetMapping("/")
	public String goToIndexPage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String goToLoginPage() {
		return "login";
	}
			
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		String user = (String) session.getAttribute("current_user");
		session.invalidate();
		logger.info(user + " has been successfully logged out.");
		return "redirect:/";
	}
	
	@GetMapping("/home")
	public String goToHomePage() {
	        return "home";
	}
	
	@GetMapping("/donor")
	public String goToDonorPage(HttpSession session) {
		if (session.getAttribute("current_user") != null) {
			return "donor";}
		else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/beneficiary")
	public String goToBeneficiaryPage(HttpSession session) {
		if (session.getAttribute("current_user") != null) {
			return "beneficiary";
			}
		else {
			return "redirect:/login";
			}
	}
	
	@GetMapping("/donation")
	public String goToDonationPage(HttpSession session) {
		if (session.getAttribute("current_user") != null) {
		return "donation";
		}
		else {
			return "redirect:/login";
			}
	}
}
