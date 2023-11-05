package com.junit.sercive.repository;

import java.util.Optional;

import com.junit.domain.User;

public interface UserRepository {

	User save(User user);
	
	Optional<User> getUserByEmail(String email);
}
