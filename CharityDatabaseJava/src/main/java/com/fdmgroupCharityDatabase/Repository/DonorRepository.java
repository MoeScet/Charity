package com.fdmgroupCharityDatabase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroupCharityDatabase.Model.Donor;

@Repository
public interface DonorRepository  extends JpaRepository<Donor, Long>{
	List<Donor> findByLastName(String lastName);
	List<Donor> findByEmail(String email);
	List<Donor> findByFirstNameAndLastName(String firstName, String lastName);
	List<Donor> findByLastNameAndEmail(String lastName, String email);
	List<Donor> findByMobile(String mobile);
	List<Donor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String lastName, String email);
	Optional<Donor> findById(Long id);
}
