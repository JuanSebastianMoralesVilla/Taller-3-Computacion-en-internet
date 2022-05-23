package com.taller1SM.repositories;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taller1SM.model.user.User;
import com.taller1SM.model.user.UserType;

@Repository
public interface  UserRepository extends CrudRepository<User,Long> {

	
List<User> findByName(String name);
	
	List<User> findByType(UserType patient);
	
	List<User> findByUsername(String username);
}
