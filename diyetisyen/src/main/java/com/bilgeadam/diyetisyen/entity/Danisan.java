package com.bilgeadam.diyetisyen.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;

import lombok.*;


@Entity
@Table(name="Danisan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Danisan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	private Integer age;
	private Male male;
	private Double weight;
	private Double height;
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "dyt_id")
	private Diyetisyen insertedDiyetisyen;
	
	@OneToOne(mappedBy = "danisan")
	@JoinColumn(name="programId")
	private Program program;
	private String username;
	@Length(min = 8)
	private String password;
	@Email(message = "Email Hatalı")
	private String email;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(
			name = "danisan_roles",
			joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
	private  List<Role> roles ;
	
	private Boolean active;
	private LocalDateTime insertedDate;
	
	
}
