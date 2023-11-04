package com.junit.domain;

import com.junit.domain.exceptions.ValidationException;

public class User {
	private Long id;
	private String name;
	private String email;
	private String password;

	public User(Long id, String name, String email, String password) {
		if (name == null) {
			throw new ValidationException("Invalid name");
		} else if (email == null) {
			throw new ValidationException("Invalid email");
		} else if (password == null) {
			throw new ValidationException("Invalid password");
		}

		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
