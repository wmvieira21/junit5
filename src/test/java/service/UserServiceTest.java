package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.junit.domain.User;
import com.junit.domain.exceptions.ValidationException;
import com.junit.sercive.UserService;
import com.junit.sercive.repository.UserRepository;

import domain.builder.UserBuilder;
import infra.UserDummyRepository;

public class UserServiceTest {

	private UserService userService;

	@Test
	public void saveValidUserWithSucess() {
		userService = new UserService(new UserDummyRepository());
		User user = UserBuilder.getInstanceUserBuilder().withID(null).withEmail("aaa@gmail.com").now();
		User userSaved = userService.save(user);

		assertNotNull(userSaved.getId());
	}

	@Test
	public void emailDuplicated() {
		userService = new UserService(new UserDummyRepository());

		assertThrows(ValidationException.class, () -> {
			User user = UserBuilder.getInstanceUserBuilder().withID(null).now();
			userService.save(user);
		});
	}

	@Test
	public void userByEmailNotFound() {
		UserRepository repo = Mockito.mock(UserRepository.class);
		userService = new UserService(repo);
		Optional<User> user = userService.getUserByEmail("default@gmail.com");
		
		assertTrue(user.isEmpty());
	}
}
