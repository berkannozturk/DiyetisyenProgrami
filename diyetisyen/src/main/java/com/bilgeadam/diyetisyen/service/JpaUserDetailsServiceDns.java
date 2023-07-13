package com.bilgeadam.diyetisyen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bilgeadam.diyetisyen.DTO.DanisanDTO;
import com.bilgeadam.diyetisyen.entity.Danisan;
import com.bilgeadam.diyetisyen.entity.Diyetisyen;
import com.bilgeadam.diyetisyen.repository.DanisanRepository;

@Service
public class JpaUserDetailsServiceDns implements UserDetailsService {

	
	@Autowired
	private DanisanRepository dnsRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return dnsRepo.findByUsername(username).map(DanisanDTO::new).orElseThrow(() -> new UsernameNotFoundException("Aranan Danisan bulunamadı"));
	}
	public Danisan getUserByUsername(String username) throws UsernameNotFoundException{
		return dnsRepo.findByUsername(username).get();
	}
	public List<Danisan> getAllDanisanlar(){
		return dnsRepo.findAll();
	}
	
	public Danisan add(Danisan danisan) {
		return dnsRepo.save(danisan);
	}
	
	public boolean checkEmail(String email) {
		return dnsRepo.existsByEmail(email);
	}

	public List<Danisan> getDanisanlarFromQuery(String keyword) {
		
		return dnsRepo.getDanisanlarLikeKeyword(keyword);
	}

	public Danisan getDanisanById(Integer id) {
		Optional<Danisan> danisan = dnsRepo.findById(id);
		if(danisan.isPresent()) {
			return danisan.get();
		}else {
			throw new UsernameNotFoundException("Kullanıcı bulunamadı.");
		}
		
	}

	public void update(Danisan existingDanisan) {
		
		dnsRepo.saveAndFlush(existingDanisan);
		
	}

	public void delete(Danisan existingDanisan) {
		
		existingDanisan.setActive(false);
		dnsRepo.saveAndFlush(existingDanisan);
		
	}
}
