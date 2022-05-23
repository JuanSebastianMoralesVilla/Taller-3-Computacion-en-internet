package com.taller1SM.security;


import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taller1SM.repositories.UserRepository;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public MyCustomUserDetailsService(UserRepository userRepo) {
		this.userRepository = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.taller1SM.model.user.User userApp = userRepository.findByUsername(username).get(0);
		if (userApp != null) {
			User.UserBuilder builder = User.withUsername(userApp.getUsername()).password(userApp.getPassword()).roles(userApp.getType().toString());
			
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
	
	
}