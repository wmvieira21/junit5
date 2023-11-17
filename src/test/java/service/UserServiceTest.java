package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.domain.User;
import com.junit.domain.exceptions.ValidationException;
import com.junit.sercive.repository.UserRepository;
import com.junit.service.UserService;

import domains.builder.UserBuilder;
import infra.UserDummyRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	/*
	 * No need due to @ExtendWith
	 * 
	 * @BeforeEach public void setup() { userRepository =
	 * Mockito.mock(UserRepository.class); userService = new
	 * UserService(userRepository); MockitoAnnotations.openMocks(this); }
	 */

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
		Mockito.when(userRepository.getUserByEmail("default@gmail.com")).thenReturn(Optional.empty());

		Optional<User> user = userService.getUserByEmail("default@gmail.com");

		assertTrue(user.isEmpty());
	}

	@Test
	public void returnUserByEmail() {
//		UserRepository repo = Mockito.mock(UserRepository.class);
//		userService = new UserService(repo);

		Optional<User> userMock = Optional
				.of(UserBuilder.getInstanceUserBuilder().withEmail("default@gmail.com").now());

		Mockito.when(userRepository.getUserByEmail("default@gmail.com")).thenReturn(userMock, userMock);
		// .thenReturn(null);

		Optional<User> user = userService.getUserByEmail("default@gmail.com");
		user = userService.getUserByEmail("a@gmail.com");
		user = userService.getUserByEmail("default@gmail.com");

		assertEquals("default@gmail.com", user.get().getEmail());

		// Mockito.verify(repo,
		// Mockito.atLeastOnce()).getUserByEmail("default@gmail.com");
		Mockito.verify(userRepository, Mockito.times(2)).getUserByEmail("default@gmail.com");
		Mockito.verify(userRepository, Mockito.times(1)).getUserByEmail("a@gmail.com");
		Mockito.verify(userRepository, Mockito.never()).getUserByEmail("asas@gmail.com");
		Mockito.verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void saveUserSuccessMock() {
		User user = UserBuilder.getInstanceUserBuilder().withID(null).now();

		Mockito.when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(user)).thenReturn(UserBuilder.getInstanceUserBuilder().now());

		User u = userService.save(user);
		assertNotNull(u.getId());

		verify(userRepository).getUserByEmail(user.getEmail());
		verify(userRepository).save(user);
	}

	@Test
	public void rejectExistingUser() {
		User newUser = UserBuilder.getInstanceUserBuilder().withID(null).withEmail("will@gmail.com").now();

		Mockito.when(userRepository.getUserByEmail(newUser.getEmail())).thenReturn(
				Optional.of(UserBuilder.getInstanceUserBuilder().withID(5L).withEmail("will@gmail.com").now()));

		assertThrows(ValidationException.class, () -> userService.save(newUser));

		verify(userRepository, Mockito.never()).save(newUser);
	}
}
