package com.taller1SM.service;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller1SM.model.user.User;
import com.taller1SM.model.user.UserType;
import com.taller1SM.repositories.UserRepository;


@Service
public class UserServiceImp  implements UserService{
	
private UserRepository userRepository;
	
	@Autowired
	public UserServiceImp(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Iterable<User> findAllAdministrators() {
		return userRepository.findByType(UserType.admin);
	}

	@Override
	public Iterable<User> findAllOperators() {
		return userRepository.findByType(UserType.operador);
	}


	@Override
	public UserType[] getTypes() {
		return UserType.values();
	}

}
