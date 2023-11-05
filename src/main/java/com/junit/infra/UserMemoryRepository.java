package com.junit.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.junit.domain.User;
import com.junit.sercive.repository.UserRepository;

public class UserMemoryRepository implements UserRepository {

	private List<User> users;
	private AtomicLong currentId = new AtomicLong();

	public UserMemoryRepository() {
		users = new ArrayList<User>();
		save(new User(null, "User 1", "a@a.com", "123456"));
	}

	@Override
	public User save(User user) {
		User userTemp = new User(currentId.incrementAndGet(), user.getName(), user.getEmail(), user.getPassword());
		users.add(userTemp);
		return userTemp;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
	}
}
