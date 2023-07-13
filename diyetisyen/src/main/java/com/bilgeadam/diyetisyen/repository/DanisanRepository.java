package com.bilgeadam.diyetisyen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bilgeadam.diyetisyen.entity.Danisan;


public interface DanisanRepository extends JpaRepository<Danisan, Integer> {
	
	@Query("SELECT d FROM Danisan d WHERE d.username = :username AND d.active=TRUE")
	Optional<Danisan> findByUsername(String username);

	boolean existsByEmail(String email);

	
	@Query("SELECT d FROM Danisan d WHERE d.username LIKE CONCAT('%',:keyword,'%') "
			+ " OR d.firstName LIKE CONCAT('%',:keyword,'%') OR d.lastName LIKE CONCAT('%',:keyword,'%')")
	List<Danisan> getDanisanlarLikeKeyword(@Param("keyword") String keyword);

	
	
}
