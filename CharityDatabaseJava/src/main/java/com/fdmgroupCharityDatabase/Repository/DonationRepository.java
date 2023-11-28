package com.fdmgroupCharityDatabase.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroupCharityDatabase.Model.Beneficiary;
import com.fdmgroupCharityDatabase.Model.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long>{
	List<Donation> findByDate(LocalDate date);
	List<Donation> findByDonorDonorId (Long donorId);
	List<Donation> findByBeneficiaryBeneficiaryId(Long beneficiaryId);
	List<Donation> findByDateBetween(LocalDate startDate, LocalDate endDate);
	Optional<Donation> findById(Long id);
 }
