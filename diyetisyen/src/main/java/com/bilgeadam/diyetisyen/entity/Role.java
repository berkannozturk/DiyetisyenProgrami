package com.bilgeadam.diyetisyen.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	private Integer id;
	
	private String name;
	
	@ManyToMany(mappedBy="roles")
	private final Collection<Diyetisyen> diyetisyen = new ArrayList<Diyetisyen>();
	
	@ManyToMany(mappedBy="roles")
	private final Collection<Danisan> danisan = new ArrayList<Danisan>();
}
