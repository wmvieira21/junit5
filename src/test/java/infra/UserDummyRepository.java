package infra;

import java.util.Optional;

import com.junit.domain.User;
import com.junit.sercive.repository.UserRepository;

import domain.builder.UserBuilder;

public class UserDummyRepository implements UserRepository {

	@Override
	public User save(User user) {
		return UserBuilder.getInstanceUserBuilder().withName(user.getName()).withEmail(user.getEmail())
				.withPassword(user.getPassword()).now();
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		if ("default@gmail.com".equalsIgnoreCase(email)) {
			return Optional.of(UserBuilder.getInstanceUserBuilder().withEmail(email).now());
		}
		return Optional.empty();
	}
}
