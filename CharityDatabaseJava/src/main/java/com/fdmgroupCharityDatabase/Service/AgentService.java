package com.fdmgroupCharityDatabase.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroupCharityDatabase.Model.Agent;
import com.fdmgroupCharityDatabase.Repository.AgentRepository;



@Service
public class AgentService {
	
	@Autowired
	private AgentRepository agentRepo;
	
	public boolean verifyAgent(String agentId, String password) {
		Optional<Agent> userOptional = agentRepo.findByAgentId(agentId);
		
		if(userOptional.isEmpty()) {
			return false;
		} else {
			return userOptional.get().getPassword().equals(password);
		}
		
	}
	
	public Agent findAgent(String id) {
		return agentRepo.findByAgentId(id).get();
	}
	
}
