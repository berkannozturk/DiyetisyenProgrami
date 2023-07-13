package com.bilgeadam.diyetisyen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bilgeadam.diyetisyen.DTO.DiyestisyenDTO;
import com.bilgeadam.diyetisyen.entity.Diyetisyen;
import com.bilgeadam.diyetisyen.repository.DiyetisyenRepository;

@Service
public class JpaUserDetailsServiceDyt implements UserDetailsService {

	@Autowired
	private DiyetisyenRepository dytRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return dytRepo.findByUsername(username).map(DiyestisyenDTO::new).orElseThrow(() -> new UsernameNotFoundException("Aranan Diyetisyen bulunamadı"));
	}
	public Diyetisyen getUserById(String username) throws UsernameNotFoundException{
		return dytRepo.findByUsername(username).get();
	}
	public List<Diyetisyen> getAllDiyetisyenler(){
		return dytRepo.findAll();
	}
	
	public Diyetisyen add(Diyetisyen diyetisyen) {
		return dytRepo.save(diyetisyen);
	}
	
	public boolean checkEmail(String email) {
		return dytRepo.existsByEmail(email);
	}

	public List<Diyetisyen> getDiyetisyenlerFromQuery(String keyword) {
		
		return dytRepo.getDiyetisyenlerLikeKeyword(keyword);
	}

	public Diyetisyen getDiyetisyenById(Integer id) {
		Optional<Diyetisyen> diyetisyen = dytRepo.findById(id);
		if(diyetisyen.isPresent()) {
			return diyetisyen.get();
		}else {
			throw new UsernameNotFoundException("Kullanıcı bulunamadı.");
		}
		
	}

	public void update(Diyetisyen existingDiyetisyen) {
		
		dytRepo.saveAndFlush(existingDiyetisyen);
		
	}

	public void delete(Diyetisyen existingDiyetisyen) {
		
		existingDiyetisyen.setActive(false);
		dytRepo.saveAndFlush(existingDiyetisyen);
	}
}
