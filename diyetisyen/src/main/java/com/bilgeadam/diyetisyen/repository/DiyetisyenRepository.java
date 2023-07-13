package com.bilgeadam.diyetisyen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bilgeadam.diyetisyen.entity.Diyetisyen;

public interface DiyetisyenRepository extends JpaRepository<Diyetisyen, Integer> {

	@Query("SELECT d FROM Diyetisyen d WHERE d.username = :username AND d.active=TRUE")
	Optional<Diyetisyen> findByUsername(String username);

	boolean existsByEmail(String email);

	@Query("SELECT d FROM Diyetisyen d WHERE d.username LIKE CONCAT('%',:keyword,'%') "
			+ " OR d.firstName LIKE CONCAT('%',:keyword,'%') OR d.lastName LIKE CONCAT('%',:keyword,'%')")
	List<Diyetisyen> getDiyetisyenlerLikeKeyword(String keyword);

	

	
	
}
