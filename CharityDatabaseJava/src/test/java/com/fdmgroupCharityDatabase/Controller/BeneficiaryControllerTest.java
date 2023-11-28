package com.fdmgroupCharityDatabase.Controller;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdmgroupCharityDatabase.Model.Beneficiary;
import com.fdmgroupCharityDatabase.Service.BeneficiaryService;
import com.fdmgroupCharityDatabase.Service.DonationService;

import jakarta.servlet.http.HttpSession;

class BeneficiaryControllerTest {

	 @InjectMocks
	    private BeneficiaryController beneficiaryController;

	    @Mock
	    private BeneficiaryService beneficiaryService;

	    @Mock
	    private DonationService donationService;

	    @Mock
	    private HttpSession session;

	    @Mock
	    private Model model;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testGoToBeneficiaryAddWithLoggedInUser() {
	        // Set up the HttpSession to simulate a logged-in user
	        when(session.getAttribute("current_user")).thenReturn("testuser");

	        // Call the controller method
	        String viewName = beneficiaryController.goToBeneficiaryAdd(model, session);

	        // Verify that the view name is correct
	        assertEquals("beneficiary-add", viewName);

	        // Verify that the beneficiary object was added to the model
	        verify(model).addAttribute(eq("beneficiary"), any(Beneficiary.class));

	        // Verify that the beneficiary service was not called
	        verifyNoInteractions(beneficiaryService);

	        // Verify that the donation service was not called
	        verifyNoInteractions(donationService);
	    }
	    
	    @Test
	    void testGoToBeneficiaryAddWithLoggedOutUser() {
	        // Set up the HttpSession to simulate a logged-out user
	        when(session.getAttribute("current_user")).thenReturn(null);

	        // Call the controller method
	        String viewName = beneficiaryController.goToBeneficiaryAdd(model, session);

	        // Verify that the view name is correct
	        assertEquals("redirect:/login", viewName);

	        // Verify that the beneficiary object was not added to the model
	        verifyNoInteractions(model);

	        // Verify that the beneficiary service was not called
	        verifyNoInteractions(beneficiaryService);

	        // Verify that the donation service was not called
	        verifyNoInteractions(donationService);
	    }

//	@Test
//	void testAddBeneficiary() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGoToBeneficiaryProfile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testShowDonations() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteBeneficiary() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGoToBeneficiaryUpdate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateBeneficiary() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetBeneficiaryList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSearchBeneficiaries() {
//		fail("Not yet implemented");
//	}

}
