import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;

import com.junit.domain.User;
import com.junit.domain.exceptions.ValidationException;
import com.junit.infra.UserMemoryRepository;
import com.junit.sercive.UserService;

import domain.builder.UserBuilder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceWithRepositoryTest {

	private static UserService userService = new UserService(new UserMemoryRepository());

	@Test
	@Order(1)
	public void saveValidUser() {
		User user = userService.save(UserBuilder.getInstanceUserBuilder().withID(null).now());
		assertNotNull(user.getId());
	}

	@Test
	@Order(2)
	public void emailDuplicatedException() {
		assertThrows(ValidationException.class, () -> {
			userService.save(UserBuilder.getInstanceUserBuilder().withID(null).now());
		});
	}
}
