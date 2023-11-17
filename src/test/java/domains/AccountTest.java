package domains;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.junit.domain.Account;
import com.junit.domain.User;
import com.junit.domain.exceptions.ValidationException;

import domains.builder.AccountBuilder;
import domains.builder.UserBuilder;


//Using TDD

public class AccountTest {

	@Test
	public void createValidaAccount() {
		// create an account
		Account account = AccountBuilder.getInstanceAccount().withID(1L).now();
		// Assertions
		assertAll("account", () -> assertNotNull(account), () -> assertEquals(1L, account.getId()),
				() -> assertEquals("default", account.getName()),
				() -> assertEquals(UserBuilder.getInstanceUserBuilder().now(), account.getUser()));
	}

	
	@ParameterizedTest
	@MethodSource(value = "dataProviderAccount")
	public void rejectInvalidAccount(Long id, String name, User user, String errorMessage) {
		String error = assertThrows(ValidationException.class, () -> {
			AccountBuilder.getInstanceAccount().withID(id).withName(name).withUser(user).now();
		}).getMessage();

		assertEquals(errorMessage, error);
	}
	
	private static Stream<Arguments> dataProviderAccount(){
		return Stream.of(
				Arguments.of(1L, null, UserBuilder.getInstanceUserBuilder().now(), "Invalid name"),
				Arguments.of(1L, "William Vieira", null, "Invalid user"));
	}
}