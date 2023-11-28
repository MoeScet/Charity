package com.fdmgroupCharityDatabase.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroupCharityDatabase.Model.Agent;

@Repository
public interface AgentRepository  extends JpaRepository<Agent, Long>{
	Optional<Agent> findByAgentId(String agentId);
}

