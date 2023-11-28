package com.fdmgroupCharityDatabase.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroupCharityDatabase.Model.Agent;
import com.fdmgroupCharityDatabase.Repository.AgentRepository;

@Service
public class AgentDetailsService implements UserDetailsService{

	@Autowired
	private AgentRepository agentRepo;

	@Override
	public UserDetails loadUserByUsername(String agentId) throws UsernameNotFoundException {

		Optional<Agent> agentOptional = agentRepo.findByAgentId(agentId);

		Agent agent = agentOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new AgentPrincipal(agent);
	}

}

