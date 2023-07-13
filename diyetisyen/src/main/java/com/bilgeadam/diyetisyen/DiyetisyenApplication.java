package com.bilgeadam.diyetisyen;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bilgeadam.diyetisyen.entity.Danisan;
import com.bilgeadam.diyetisyen.entity.Diyetisyen;
import com.bilgeadam.diyetisyen.entity.Male;
import com.bilgeadam.diyetisyen.entity.Program;
import com.bilgeadam.diyetisyen.entity.Role;
import com.bilgeadam.diyetisyen.repository.DanisanRepository;
import com.bilgeadam.diyetisyen.repository.DiyetisyenRepository;
import com.bilgeadam.diyetisyen.repository.RoleRepository;
import com.bilgeadam.diyetisyen.service.JpaUserDetailsServiceDns;
import com.bilgeadam.diyetisyen.service.JpaUserDetailsServiceDyt;

@SpringBootApplication
public class DiyetisyenApplication {

	
	
	
	public static void main(String[] args) {
		SpringApplication.run(DiyetisyenApplication.class, args);
	}

	
	
	
	@Bean
	CommandLineRunner runner(JpaUserDetailsServiceDyt dytService ,JpaUserDetailsServiceDns dnsService, RoleRepository roleRepo,DiyetisyenRepository dytRepo, DanisanRepository dnsRepo, PasswordEncoder encoder) {
		
		Role role1 = new Role(1,"ROLE_BASDYT");
		Role role2 = new Role(2,"ROLE_DYT");
		Role role3 = new Role(3,"ROLE_DNS");
		
		roleRepo.save(role1);
		roleRepo.save(role2);
		roleRepo.save(role3);
		
		List<Role> basDytRole = new ArrayList<>();
		List<Role> dytRole = new ArrayList<>();
		List<Role> dnsRole = new ArrayList<>();
		
		basDytRole.add(role1);
		dytRole.add(role2);
		dnsRole.add(role3);
		
		Diyetisyen d1 = new Diyetisyen(null,"Leyla","Tan","5555555555","leyla@tan.com", encoder.encode("123456789") ,"leyla@tan.com",basDytRole,true,LocalDateTime.now());
		Diyetisyen d2 = new Diyetisyen(null,"Fatma","Şen","5555555551", "fatma@sen.com", encoder.encode("1234567891") ,"fatma@sen.com",dytRole,true,LocalDateTime.now());
		
		return args ->{
			if(!dytService.checkEmail("leyla@tan.com")) {
			dytRepo.save(d1);
			}
			if(!dytService.checkEmail("fatma@sen.com")) {
			dytRepo.save(d2);
			}
			if(!dnsService.checkEmail("berkan@ozturk.com")) { 
			dnsRepo.save(new Danisan(null,"Berkan","Öztürk",24, Male.ERKEK, 82.0,1.81,"5542222222",d1,null,"berkan@ozturk.com",encoder.encode("456"),"berkan@ozturk.com",dnsRole,true,LocalDateTime.now()));
			}
			if(!dnsService.checkEmail("alex@desouza.com")) {
			dnsRepo.save(new Danisan(null,"Alex De","Souza",42, Male.ERKEK, 75.0,1.75,"5542222907",d2,null,"alex@desouza.com",encoder.encode("1907"),"alex@desouza.com",dnsRole,true,LocalDateTime.now()));
			}
	};
	
	}
}

