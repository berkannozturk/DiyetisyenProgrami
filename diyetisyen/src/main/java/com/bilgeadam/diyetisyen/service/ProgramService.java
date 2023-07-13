package com.bilgeadam.diyetisyen.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.bilgeadam.diyetisyen.entity.Program;
import com.bilgeadam.diyetisyen.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import com.bilgeadam.diyetisyen.repository.ProgramRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramService {

	
	private final ProgramRepository programRepo;

	public Program add(Program program) {
		
		program.setInsertedDate(LocalDateTime.now());
		log.info("Yeni program kaydedildi");
		return programRepo.save(program);
	}

	public Program update(Program p) {
		return programRepo.saveAndFlush(p);

	}
}
