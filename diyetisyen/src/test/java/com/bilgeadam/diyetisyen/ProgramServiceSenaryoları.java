package com.bilgeadam.diyetisyen;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.bilgeadam.diyetisyen.entity.Program;
import com.bilgeadam.diyetisyen.repository.ProgramRepository;
import com.bilgeadam.diyetisyen.service.ProgramService;

public class ProgramServiceSenaryoları {

	
	private ProgramService service;
	@Mock
	private ProgramRepository repo;
	
	@BeforeEach
	void initialUseCase() {
		service = new ProgramService(repo);
	}
	
	@Test
	void savedProgram() {
		Program p = new Program(null, "salam", "sosis", "corba", "makarna", "su", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	
		service.add(p);
		
		assertThat(p.getInsertedDate()).isNotNull();  
	}
	
	
}
