package com.bilgeadam.diyetisyen.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Program")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	private String icerik1;
	private String icerik2;
	private String icerik3;
	private String icerik4;
	private String icerik5;
	private String icerik6;
	private String icerik7;
	private String icerik8;
	private String icerik9;
	private String icerik10;
	private String icerik11;
	private String icerik12;
	private String icerik13;
	private String icerik14;
	private String icerik15;
	private String icerik16;
	private String icerik17;
	private String icerik18;
	private String icerik19;
	private String icerik20;
	private String icerik21;
	
	
	private LocalDateTime insertedDate;
	
	@OneToOne()
	@JoinColumn(name="danisanId")
	private Danisan danisan;
	
	
}
