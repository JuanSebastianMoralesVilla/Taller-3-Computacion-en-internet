package com.taller1SM.service;

import java.util.Optional;

import com.taller1SM.model.user.User;
import com.taller1SM.model.user.UserType;


public interface UserService {
	
	
	public void save(User user);

	public Optional<User> findById(long id);

	public Iterable<User> findAll();

	public Iterable<User> findAllAdministrators();

	public Iterable<User> findAllOperators();



	public UserType[] getTypes();

}
