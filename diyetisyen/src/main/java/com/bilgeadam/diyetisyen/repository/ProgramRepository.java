package com.bilgeadam.diyetisyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bilgeadam.diyetisyen.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Integer> {

	
}
