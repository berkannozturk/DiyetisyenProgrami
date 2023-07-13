package com.bilgeadam.diyetisyen.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bilgeadam.diyetisyen.entity.Danisan;
import com.bilgeadam.diyetisyen.entity.Role;

public class DanisanDTO implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5189481665598897024L;
	private Danisan danisan;
	
	public DanisanDTO(Danisan danisan) {
		this.danisan=danisan;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Role  role : danisan.getRoles()) {
			
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return danisan.getPassword();
	}

	@Override
	public String getUsername() {
		
		return danisan.getUsername();
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
		
		return danisan.getActive();
	}
	
	
	
	
}
