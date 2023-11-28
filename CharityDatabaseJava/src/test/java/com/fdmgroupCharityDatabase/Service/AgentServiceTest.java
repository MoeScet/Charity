package com.fdmgroupCharityDatabase.Service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroupCharityDatabase.Model.Agent;
import com.fdmgroupCharityDatabase.Repository.AgentRepository;

@SpringBootTest
class AgentServiceTest {
	
	@Autowired
	private AgentService agentService;
	
	@MockBean
	private AgentRepository agentRepo;

	@BeforeEach
	void setUp() {
	    Optional<Agent> agent = Optional.of(new Agent("agent0", "Name", "1234"));
	    when(agentRepo.findByAgentId("agent0")).thenReturn(agent);
	}

	@Test
	void testFindAgent() {
		String agent_id = "agent0";
		String agent_name = "Name";
		String agent_password = "1234";
		
		//Find agent by using agent ID
		Agent agentById = agentService.findAgent(agent_id);
		
		
		//Call the getAttribute methods for each attribute to check if the attributes returned are correct
		assertEquals(agent_id, agentById.getAgentId());
		assertEquals(agent_name, agentById.getName());
		assertEquals(agent_password, agentById.getPassword());
		
	}
	
	@Test
	void testVerifyAgent() {

	    // Call the verifyAgent method with the correct ID and password and check if it returns true
	    assertTrue(agentService.verifyAgent("agent0", "1234"));

	    // Call the verifyAgent method with an incorrect password and check if it returns false
	    assertFalse(agentService.verifyAgent("agent0", "5678"));

	    // Call the verifyAgent method with a non-existing ID and check if it returns false
	    assertFalse(agentService.verifyAgent("agent1", "1234"));
	}


}
