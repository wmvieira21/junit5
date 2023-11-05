import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import com.junit.domain.User;
import com.junit.domain.exceptions.ValidationException;

import domain.builder.UserBuilder;

public class UserTest {

	private User user;

	@BeforeEach
	public void setup() {
	}

	@Test
	@DisplayName("Create a valid user")
	public void createValideUser() {
		user = UserBuilder.getInstanceUserBuilder().withID(1L).withName("william").withEmail("a@a.com")
				.withPassword("asas").now();

		assertAll("user assertions", () -> assertNotNull(user), () -> assertEquals(1L, user.getId()));
	}

	@Test
	@DisplayName("Invalid name exception")
	public void nameNullException() {
		ValidationException assertThrows2 = assertThrows(ValidationException.class, () -> {
			// user = new User(1L, null, "a@a.com", "asas45a54sa54");
			user = UserBuilder.getInstanceUserBuilder().withName(null).now();
		});

		assertEquals("Invalid name", assertThrows2.getMessage());
	}

	@Test
	@DisplayName("Invalid email exception")
	public void emailNullException() {
		ValidationException assertThrows2 = assertThrows(ValidationException.class, () -> {
			user = UserBuilder.getInstanceUserBuilder().withEmail(null).now();
		});

		assertEquals("Invalid email", assertThrows2.getMessage());
	}

	@Test
	@DisplayName("Invalid password exception")
	public void passwordNullException() {
		ValidationException assertThrows2 = assertThrows(ValidationException.class, () -> {
			user = UserBuilder.getInstanceUserBuilder().withPassword(null).now();
		});

		assertEquals("Invalid password", assertThrows2.getMessage());
	}

	@ParameterizedTest(name = "{4}")
	@CsvSource(value = { "0, null, a@a.com, kjkjksdh, Invalid name", "0, william, null, kjkjksdh, Invalid email",
			"0, william, a@a.com, null, Invalid password", }, nullValues = "null")
	@DisplayName("Validade user exceptions")
	public void validateUserDataExceptions(Long id, String name, String email, String password,
			String exceptionMessage) {
		ValidationException assertThrows2 = assertThrows(ValidationException.class, () -> {
			user = UserBuilder.getInstanceUserBuilder().withID(id).withName(name).withEmail(email)
					.withPassword(password).now();
		});
		assertEquals(exceptionMessage, assertThrows2.getMessage());
	}

	@ParameterizedTest(name = "{index} - {4}")
	@CsvFileSource(resources = "mandatoryFields.csv", nullValues = "null"/*,useHeadersInDisplayName = true, numLinesToSkip = 1*/)
	@DisplayName("Validade user exceptions file")
	public void validateUserDataExceptionsFile(Long id, String name, String email, String password,
			String exceptionMessage) {
		ValidationException assertThrows2 = assertThrows(ValidationException.class, () -> {
			user = UserBuilder.getInstanceUserBuilder().withID(id).withName(name).withEmail(email)
					.withPassword(password).now();
		});
		assertEquals(exceptionMessage, assertThrows2.getMessage());
	}
	
	
	
	
	
	
	
	
}
