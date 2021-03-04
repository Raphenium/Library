package com.bezkoder.spring.datajpa.repository;

import java.util.Optional;

import com.bezkoder.spring.datajpa.model.User;

public interface UserCrudService{
	User save(User user);

	  Optional<User> find(String id);

	  Optional<User> findByUsername(String username);
}
