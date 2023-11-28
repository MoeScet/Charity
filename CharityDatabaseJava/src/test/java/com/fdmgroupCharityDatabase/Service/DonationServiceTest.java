package com.fdmgroupCharityDatabase.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroupCharityDatabase.Model.Agent;
import com.fdmgroupCharityDatabase.Model.Beneficiary;
import com.fdmgroupCharityDatabase.Model.Donation;
import com.fdmgroupCharityDatabase.Model.Donor;
import com.fdmgroupCharityDatabase.Repository.DonationRepository;

@SpringBootTest
class DonationServiceTest {
	
	@Autowired
	private DonationService donationService;
	
	@Autowired
	private DonorService donorService;
	
	@Autowired
	private BeneficiaryService benefService;
	
	@MockBean
	private DonationRepository donationRepo;

	@Test
    public void testFindAllDonations() {
        // Create some dummy data
        List<Donation> donations = new ArrayList<>();
        Donor donor = new Donor();
        Beneficiary beneficiary = new Beneficiary();
        Agent agent = new Agent();
        Donation donation1 = new Donation(donor, beneficiary, 100.0, LocalDate.now(), "type1");
        Donation donation2 = new Donation(donor, beneficiary, 200.0, LocalDate.now(), "type2");
        Donation donation3 = new Donation(donor, beneficiary, 300.0, LocalDate.now(), "type3");
        donation1.setAgent(agent);
        donation2.setAgent(agent);
        donation3.setAgent(agent);
        donations.add(donation1);
        donations.add(donation2);
        donations.add(donation3);

        // Set up mock behavior for the repository
        when(donationRepo.findAll()).thenReturn(donations);

        // Call the service method
        List<Donation> result = donationService.findAllDonations();

        // Verify the result
        assertEquals(3, result.size());
        assertEquals(donation1, result.get(0));
        assertEquals(donation2, result.get(1));
        assertEquals(donation3, result.get(2));

        // Verify that the repository method was called once
        verify(donationRepo, times(1)).findAll();
    }

	@Test
	public void testSaveDonation() {
	    // Create a new donation
	    Donor donor = new Donor("John", "Doe", "johndoe@example.com", "1234567890");
	    Beneficiary beneficiary = new Beneficiary("Jane Smith", "janesmith@example.com");
	    Donation donation = new Donation(donor, beneficiary, 100.0, LocalDate.now(), "IN");

	    // Save the donation
	    donationService.saveDonation(donation);

	    // Check if the new donation was added
	    verify(donationRepo, times(1)).save(donation);
	}
	@Test
	public void testUpdateDonation() {		
		Long donationId = 1L;
		Donor donor = new Donor("John", "Doe", "johndoe@example.com", "1234567890");
	    Beneficiary beneficiary = new Beneficiary("Jane Smith", "janesmith@example.com");
	    Donation existingDonation = new Donation(donor, beneficiary, 100.0, LocalDate.now(), "IN");
	    
	    Donor newDonor = new Donor("James", "Smith", "jamessmith@example","1111111111");
	    Beneficiary newBeneficiary = new Beneficiary("Susan Doe", "susandoe@examplecom");
	    Donation updatedDonation = new Donation(newDonor, newBeneficiary, 200.0, LocalDate.now(), "IN");

	    when(donationRepo.findById(donationId)).thenReturn(Optional.of(existingDonation));
	    when(donationRepo.save(existingDonation)).thenReturn(updatedDonation);

	    Donation result = donationService.updateDonation(donationId, updatedDonation);
	    
	    assertEquals(newDonor.getFirstName(), result.getDonor().getFirstName());
	    assertEquals(newDonor.getLastName(), result.getDonor().getLastName());
	    assertEquals(newDonor.getEmail(), result.getDonor().getEmail());
	    assertEquals(newDonor.getMobile(), result.getDonor().getMobile());
	    assertEquals(newBeneficiary.getName(), result.getBeneficiary().getName());
	    assertEquals(updatedDonation.getAmount(), result.getAmount());
	    assertEquals(updatedDonation.getDate(), result.getDate());
	    assertEquals(updatedDonation.getType(), result.getType());  

	    verify(donationRepo, times(1)).findById(donationId);
	    verify(donationRepo, times(1)).save(existingDonation);
	}


	@Test
    void testDeleteExistingDonation() {
		Long donationId = 1L;
		Donor donor = new Donor("John", "Doe", "johndoe@example.com", "1234567890");
	    Beneficiary beneficiary = new Beneficiary("Jane Smith", "janesmith@example.com");
	    Donation donation = new Donation(donor, beneficiary, 100.0, LocalDate.now(), "IN");

        when(donationRepo.findById(donationId)).thenReturn(Optional.of(donation));

        donationService.deleteDonation(donationId);

        verify(donationRepo, times(1)).findById(donationId);
        verify(donationRepo, times(1)).deleteById(donationId);
    }
	
//	@Test
//	public void testFindDonationsByDonorId() {
//	    // Create a donor
//	    Donor donor = new Donor("John", "Doe", "john.doe@example.com", "1234567890");
//	    Beneficiary beneficiary = new Beneficiary("Jane Smith", "janesmith@example.com");
//	    donorService.saveDonor(donor);
//
//	    // Create some donations with the donor
//	    Donation donation1 = new Donation(donor, beneficiary, 100.0, LocalDate.now(), "IN");
//	    Donation donation2 = new Donation(donor, beneficiary, 200.0, LocalDate.now(), "IN");
//	    donationService.saveDonation(donation1);
//	    donationService.saveDonation(donation2);
//
//	    // Call the findDonationsByDonorId method with the donor's ID
//	    List<Donation> donations = donationService.findDonationsByDonorId(donor.getDonorId());
//
//	    // Verify that the returned list contains both donations
//	    assertEquals(2, donations.size());
//	    assertTrue(donations.contains(donation1));
//	    assertTrue(donations.contains(donation2));
//	}

	public void testFindDonationsByBeneficiaryId() {
	    // Create a test beneficiary and save it to the database
	    Beneficiary beneficiary = new Beneficiary("Test Beneficiary", "email@example.com");
	    benefService.saveBeneficiary(beneficiary);
	    
	    // Create a few test donations and set the beneficiary field 
	    Donor donor1 = new Donor("John", "Doe", "johndoe@example.com", "1234567890");
	    Donor donor2 = new Donor("James", "Smith", "jamessmith@example","1111111111");
	    
	    Donation donation1 = new Donation(donor1, beneficiary, 100.0, LocalDate.now(), "IN");
	    donationService.saveDonation(donation1);
	    
	    Donation donation2 = new Donation(donor2, beneficiary, 200.0, LocalDate.now(), "IN");
	    donationService.saveDonation(donation2);
	    
	    // Call the findDonationsByBeneficiaryId method with the ID of the test beneficiary
	    List<Donation> donations = donationService.findDonationsByBeneficiaryId(beneficiary.getBeneficiaryId());
	    
	    // Assert that the returned list contains the expected donations
	    assertEquals(2, donations.size());
	    assertTrue(donations.contains(donation1));
	    assertTrue(donations.contains(donation2));
	}

//	@Test
//	public void testGetDonationsByDateRange() {
//	    // Create some donations with different dates
//	    Beneficiary beneficiary = new Beneficiary("Jane Smith", "janesmith@example.com");
//	    Donor donor1 = new Donor("John", "Doe", "johndoe@example.com", "1234567890");
//	    Donor donor2 = new Donor("James", "Smith", "jamessmith@example","1111111111");
//	    Donation donation1 = new Donation(donor1, beneficiary, 100.0, LocalDate.of(2023, 3, 1), "IN");
//	    Donation donation2 = new Donation(donor2, beneficiary, 200.0, LocalDate.of(2023, 3, 10), "IN");
//	    Donation donation3 = new Donation(donor1, beneficiary, 300.0, LocalDate.of(2023, 3, 15), "IN");
//	    donationService.saveDonation(donation1);
//	    donationService.saveDonation(donation2);
//	    donationService.saveDonation(donation3);
//
//	    // Call the getDonationsByDateRange method with a date range
//	    List<Donation> donations = donationService.getDonationsByDateRange(LocalDate.of(2023, 3, 5), LocalDate.of(2023, 3, 15));
//
//	    // Verify that the returned list contains the expected donations
//	    assertEquals(2, donations.size());
//	    assertTrue(donations.contains(donation2));
//	    assertTrue(donations.contains(donation3));
//	}
}
