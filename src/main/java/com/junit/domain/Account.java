package com.junit.domain;

import java.util.Objects;

import com.junit.domain.exceptions.ValidationException;

public class Account {

	private Long id;
	private String name;
	private User user;

	public Account(Long id, String name, User user) {
		if (name == null) {
			throw new ValidationException("Invalid name");
		} else if (user == null) {
			throw new ValidationException("Invalid user");
		}

		this.id = id;
		this.name = name;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public User getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(id, other.id);
	}
}
