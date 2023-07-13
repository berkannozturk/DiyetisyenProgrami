package com.bilgeadam.diyetisyen.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bilgeadam.diyetisyen.entity.Diyetisyen;
import com.bilgeadam.diyetisyen.entity.Role;

public class DiyestisyenDTO implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6056265249996649493L;
	
	private Diyetisyen diyetisyen;
	
	public DiyestisyenDTO(Diyetisyen diyetisyen) {	
		this.diyetisyen=diyetisyen;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Role  role : diyetisyen.getRoles()) {
			
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return diyetisyen.getPassword();
	}

	@Override
	public String getUsername() {
		
		return diyetisyen.getUsername();
	}

	public String getId() {

		return diyetisyen.getId().toString();
	}
	
	public Integer getIdLıkeInteger() {

		return diyetisyen.getId();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return diyetisyen.getActive();
	}
}
