package com.fdmgroupCharityDatabase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroupCharityDatabase.Model.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
	List<Beneficiary> findByName(String name);
	List<Beneficiary> findByEmail(String email);
	Optional<Beneficiary> findById(Long id);
	List<Beneficiary> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name,String email);

}
