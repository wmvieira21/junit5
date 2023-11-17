package com.junit.service;

import java.util.Optional;

import com.junit.domain.User;
import com.junit.domain.exceptions.ValidationException;
import com.junit.sercive.repository.UserRepository;

public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(User user) {
		userRepository.getUserByEmail(user.getEmail()).ifPresent(u -> {
			throw new ValidationException(String.format("Email %s already exists", u.getEmail()));
		});
		return userRepository.save(user);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
}