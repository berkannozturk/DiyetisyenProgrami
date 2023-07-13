package com.bilgeadam.diyetisyen;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bilgeadam.diyetisyen.entity.Diyetisyen;
import com.bilgeadam.diyetisyen.service.JpaUserDetailsServiceDyt;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceSenaryoları {

	@Autowired
	private JpaUserDetailsServiceDyt serviceDyt;
	
	@Test
	void savedDytTest() {
		
		Diyetisyen d = new Diyetisyen(null,"Selin","Hosgor","5555555555","Selin@Hosgor.com", "123456789" ,"Selin@Hosgor.com",null,true,LocalDateTime.now());
	
		serviceDyt.add(d);
		
		
		assertThat(d.getId()).isNotNull();
	}
	
}
