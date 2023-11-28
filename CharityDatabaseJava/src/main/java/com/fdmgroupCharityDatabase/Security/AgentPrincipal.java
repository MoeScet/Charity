package com.fdmgroupCharityDatabase.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fdmgroupCharityDatabase.Model.Agent;

public class AgentPrincipal implements UserDetails {

	private Agent agent;

	public AgentPrincipal(Agent agent) {
		super();
		this.agent = agent;
	}

	@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("AGENT"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return agent.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return agent.getAgentId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}

