package com.fdmgroupCharityDatabase.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Agent {
	
	@Id	
	private String agentId;
	
	private String name;
	private String password;
	
	public Agent(String agentId, String name, String password) {
		super();
		this.agentId = agentId;
		this.name = name;
		this.password = password;
	}
	
	public Agent() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
	
	