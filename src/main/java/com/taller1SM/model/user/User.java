package com.taller1SM.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;

	@NotBlank 
	private String username;

	@NotBlank 
	private String password;

	@NotNull 
	private UserType type;
	
	@NotBlank 
	private String name;
	
}
