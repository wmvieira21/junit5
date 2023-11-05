package com.junit.domain;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
}
