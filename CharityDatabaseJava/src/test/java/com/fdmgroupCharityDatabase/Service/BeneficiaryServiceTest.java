package com.fdmgroupCharityDatabase.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import com.fdmgroupCharityDatabase.Repository.BeneficiaryRepository;

@SpringBootTest
class BeneficiaryServiceTest {
	
	@Autowired
	private BeneficiaryService benefService;
	
	@MockBean
	private BeneficiaryRepository beneficiaryRepo;

	@BeforeEach
	void setUp() {
//	    Optional<Beneficiary> beneficiary = Optional.of(new Beneficiary("John Doe", "john.doe@example.com"));
//	    when(beneficiaryRepo.findById((long) 1)).thenReturn(beneficiary);
	}

	@Test
    void testFindAllBeneficiaries() {
        // Create a list of test Beneficiary objects
        List<Beneficiary> beneficiaries = Arrays.asList(
            new Beneficiary("John Doe", "john.doe@example.com"),
            new Beneficiary("Jane Smith", "jane.smith@example.com")
        );

        // Define the behavior of the findAll method to return the test Beneficiary objects
        when(beneficiaryRepo.findAll()).thenReturn(beneficiaries);

        // Call the findAllBeneficiaries method and check if it returns the expected list of Beneficiary objects
        List<Beneficiary> result = benefService.findAllBeneficiaries();
        assertEquals(beneficiaries, result);
    }

	@Test
	void testSaveBeneficiary() {
		// Create a test beneficiary
		Beneficiary beneficiary = new Beneficiary("John Doe", "john.doe@example.com");

		// Call the saveBeneficiary method with the test beneficiary
		benefService.saveBeneficiary(beneficiary);
		

		// Verify that the save method of the repository was called with the test beneficiary
		verify(beneficiaryRepo).save(beneficiary);
	}
	
	 @Test
	    void testFindBeneficiaryById() {
	    Long beneficiaryId = 1L;
	    Beneficiary beneficiary = new Beneficiary("John Doe", "john.doe@example.com");

	     when(beneficiaryRepo.findById(beneficiaryId)).thenReturn(Optional.of(beneficiary));

	     Optional<Beneficiary> result = benefService.findBeneficiaryById(beneficiaryId);

	     assertTrue(result.isPresent());
	     assertEquals("John Doe", result.get().getName());
	     assertEquals("john.doe@example.com", result.get().getEmail());

	     verify(beneficiaryRepo, times(1)).findById(beneficiaryId);
	    }
//
	@Test
	 void testUpdateExistingBeneficiary() {
		Long beneficiaryId = 1L;
	    Beneficiary existingBeneficiary = new Beneficiary("John Doe", "john.doe@example.com");
	    Beneficiary updatedBeneficiary = new Beneficiary("Jane Doe", "jane.doe@example.com");

	    when(beneficiaryRepo.findById(beneficiaryId)).thenReturn(Optional.of(existingBeneficiary));
	    when(beneficiaryRepo.save(existingBeneficiary)).thenReturn(updatedBeneficiary);

	    Beneficiary result = benefService.updateBeneficiary(beneficiaryId, updatedBeneficiary);

	    assertEquals(updatedBeneficiary.getName(), result.getName());
	    assertEquals(updatedBeneficiary.getEmail(), result.getEmail());

	    verify(beneficiaryRepo, times(1)).findById(beneficiaryId);
	    verify(beneficiaryRepo, times(1)).save(existingBeneficiary);
	    }

	@Test
    void testDeleteExistingBeneficiary() {
        Long beneficiaryId = 1L;
        Beneficiary beneficiary = new Beneficiary("John Doe", "john.doe@example.com");

        when(beneficiaryRepo.findById(beneficiaryId)).thenReturn(Optional.of(beneficiary));

        benefService.deleteBeneficiary(beneficiaryId);

        verify(beneficiaryRepo, times(1)).findById(beneficiaryId);
        verify(beneficiaryRepo, times(1)).deleteById(beneficiaryId);
    }
	
	 @Test
	  void testSearchBeneficiaries() {
		 String search = "John";
	     List<Beneficiary> beneficiaries = new ArrayList<>();
	     beneficiaries.add(new Beneficiary("John Doe", "john.doe@example.com"));
	     beneficiaries.add(new Beneficiary("John Smith", "john.smith@example.com"));

	     when(beneficiaryRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search))
	         .thenReturn(beneficiaries);

	     List<Beneficiary> result = benefService.searchBeneficiaries(search);

	     assertEquals(beneficiaries.size(), result.size());
	     assertEquals(beneficiaries.get(0).getName(), result.get(0).getName());
	     assertEquals(beneficiaries.get(0).getEmail(), result.get(0).getEmail());

	     verify(beneficiaryRepo, times(1)).findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
	    }

}
