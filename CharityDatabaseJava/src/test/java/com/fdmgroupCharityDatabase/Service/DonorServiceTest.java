package com.fdmgroupCharityDatabase.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroupCharityDatabase.Model.Beneficiary;
import com.fdmgroupCharityDatabase.Model.Donor;
import com.fdmgroupCharityDatabase.Repository.BeneficiaryRepository;
import com.fdmgroupCharityDatabase.Repository.DonorRepository;

@SpringBootTest
class DonorServiceTest {
	
	@Autowired
	private DonorService donorService;
	
	@MockBean
	private DonorRepository donorRepo;

	@BeforeEach
	void setUp(){
	}

	@Test
    void testFindAllDonors() {
        // Create a list of test Donor objects
        List<Donor> donors = Arrays.asList(
            new Donor ("John", "Doe", "john.doe@example.com","11111111"),
            new Donor ("Jane", "Smith", "jane.smith@example.com", "22222222")
        );

        // Define the behavior of the findAll method to return the test Donor objects
        when(donorRepo.findAll()).thenReturn(donors);

        // Call the findAllDonors method and check if it returns the expected list of Donor objects
        List<Donor> result = donorService.findAllDonors();
        assertEquals(donors, result);
    }

	@Test
	void testFindDonorById() {
	Long donorId = 1L;
	Donor donor = new Donor("John", "Doe", "john.doe@example.com","11111111");

	when(donorRepo.findById(donorId)).thenReturn(Optional.of(donor));

	Optional<Donor> result = donorService.findDonorById(donorId);

	assertTrue(result.isPresent());
	assertEquals("John", result.get().getFirstName());
	assertEquals("Doe", result.get().getLastName());
	assertEquals("john.doe@example.com", result.get().getEmail());
	assertEquals("11111111", result.get().getMobile());

	verify(donorRepo, times(1)).findById(donorId);
	}
	 
	@Test
	void testSaveDonor() {
		// Create a test donor
		Donor donor = new Donor("John", "Doe", "john.doe@example.com","11111111");
	 
		// Call the saveDonor method with the test donor
		donorService.saveDonor(donor);
	 
		// Verify that the save method of the repository was called with the test donor
		verify(donorRepo).save(donor);
	}

	@Test
	void testUpdateExistingDonor() {
		Long donorId = 1L;
		Donor existingDonor = new Donor ("John", "Doe", "john.doe@example.com","11111111");
		Donor updatedDonor = new Donor ("Jane", "Smith", "jane.smith@example.com", "22222222");

		when(donorRepo.findById(donorId)).thenReturn(Optional.of(existingDonor));
		when(donorRepo.save(existingDonor)).thenReturn(updatedDonor);

		Donor result = donorService.updateDonor(donorId, updatedDonor);

		assertEquals(updatedDonor.getFirstName(), result.getFirstName());
		assertEquals(updatedDonor.getLastName(), result.getLastName());
		assertEquals(updatedDonor.getEmail(), result.getEmail());
		assertEquals(updatedDonor.getMobile(), result.getMobile());
		

		verify(donorRepo, times(1)).findById(donorId);
		verify(donorRepo, times(1)).save(existingDonor);
		
	}	 
	 
	 
	@Test
    void testDeleteExistingDonor() {
        Long donorId = 1L;
        Donor donor = new Donor("John", "Doe", "john.doe@example.com","11111111");

        when(donorRepo.findById(donorId)).thenReturn(Optional.of(donor));

        donorService.deleteDonor(donorId);

        verify(donorRepo, times(1)).findById(donorId);
        verify(donorRepo, times(1)).deleteById(donorId);
    }

	 @Test
	  void testSearchDonors() {
		 String search = "John";
	     List<Donor> donors = new ArrayList<>();
	     donors.add(new Donor("John", "Doe", "john.doe@example.com","11111111"));
	     donors.add(new Donor("John", "Smith", "john.smith@example.com","22222222"));

	     when(donorRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, search))
	         .thenReturn(donors);

	     List<Donor> result = donorService.searchDonors(search);

	     assertEquals(donors.size(), result.size());
	     assertEquals(donors.get(0).getFirstName(), result.get(0).getFirstName());
	     assertEquals(donors.get(0).getLastName(), result.get(0).getLastName());
	     assertEquals(donors.get(0).getEmail(), result.get(0).getEmail());
	     assertEquals(donors.get(0).getMobile(), result.get(0).getMobile());

	     verify(donorRepo, times(1)).findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, search);
	    }

}
