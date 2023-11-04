package domain.builder;

import com.junit.domain.User;

public class UserBuilder {
	private Long id = 0L;
	private String name = "default";
	private String email = "default@gmail.com";
	private String password = "abc";

	private UserBuilder() {
	}

	public static UserBuilder getInstanceUserBuilder() {
		return new UserBuilder();
	}

	public UserBuilder withID(Long id) {
		this.id = id;
		return this;
	}

	public UserBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public UserBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public User now() {
		return new User(id, name, email, password);
	}
}
