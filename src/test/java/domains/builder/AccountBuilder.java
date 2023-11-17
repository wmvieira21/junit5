package domains.builder;

import com.junit.domain.Account;
import com.junit.domain.User;

public class AccountBuilder {

	private Long id = 0L;
	private String name = "default";
	private User user = UserBuilder.getInstanceUserBuilder().now();

	private AccountBuilder() {
	}

	public static AccountBuilder getInstanceAccount() {
		return new AccountBuilder();
	}

	public AccountBuilder withID(Long id) {
		this.id = id;
		return this;
	}

	public AccountBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public AccountBuilder withUser(User user) {
		this.user = user;
		return this;
	}

	public Account now() {
		return new Account(id, name, user);
	}

}
