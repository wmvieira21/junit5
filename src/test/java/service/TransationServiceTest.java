package service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.domain.Account;
import com.junit.domain.Transation;
import com.junit.domain.exceptions.ValidationException;
import com.junit.sercive.repository.TransationDAO;
import com.junit.service.TransationService;

import domains.builder.AccountBuilder;
import domains.builder.TransationBuilder;

//@EnabledIf(value = "isHourValid")
@ExtendWith(MockitoExtension.class)
public class TransationServiceTest {
	@Mock
	private TransationDAO dao;

	@InjectMocks
	@Spy
	private TransationService transationService;
	
	@Captor
	private ArgumentCaptor<Transation> captor;

//	@BeforeEach
//	public void setup() {
//		Assumptions.assumeTrue(LocalDateTime.now().getHour() < 22);
//	}

	@Test
	public void savingValidTransation() {

		Transation transation = TransationBuilder.oneTransation().withID(null).now();

		when(dao.save(Mockito.any(Transation.class))).thenReturn(TransationBuilder.oneTransation().now());

		Transation savedTransation = transationService.save(transation);

		assertAll("Transation", () -> assertEquals(1L, savedTransation.getId()), () -> {
			assertAll("Conta", () -> assertEquals("default", savedTransation.getAccount().getName()), () -> {
				assertAll("User", () -> assertEquals("default user", savedTransation.getAccount().getUser().getName()));
			});
		});
	}

	@ParameterizedTest(name = "{6}")
	@MethodSource(value = "mandatoryFieldsSource")
	public void validadeMandatoryFieldsWhenSaving(Long id, String desc, Double value, LocalDateTime date,
			Account account, boolean status, String message) {

		Transation transation = TransationBuilder.oneTransation().withID(id).withAccount(account).withDate(date)
				.withDescription(desc).withStatus(status).withValue(value).now();

		ValidationException assertThrows2 = assertThrows(ValidationException.class, () -> {
			transationService.save(transation);
		});

		assertEquals(message, assertThrows2.getMessage());
	}

	private static Stream<Arguments> mandatoryFieldsSource() {
		return Stream.of(
				Arguments.of(1L, null, 10D, LocalDateTime.now(), AccountBuilder.getInstanceAccount().now(), true,
						"Invalid Description"),
				Arguments.of(1L, "hahsa", null, LocalDateTime.now(), AccountBuilder.getInstanceAccount().now(), true,
						"Invalid Value"),
				Arguments.of(1L, "asasa", 10D, null, AccountBuilder.getInstanceAccount().now(), true, "Invalid Date"),
				Arguments.of(1L, "asas", 10D, LocalDateTime.now(), null, true, "Invalid Account"));
	}

	private static boolean isHourValid() {
		return LocalDateTime.now().getHour() < 22;
	}

	@Test
	public void savingValidTransationStaticMock() {

		Transation transation = TransationBuilder.oneTransation().withID(null).now();

		when(dao.save(Mockito.any(Transation.class))).thenReturn(TransationBuilder.oneTransation().now());

		LocalDateTime mockedDate = LocalDateTime.of(2023, 11, 16, 17, 6);

		try (MockedStatic<LocalDateTime> ldt = Mockito.mockStatic(LocalDateTime.class)) {
			ldt.when(() -> LocalDateTime.now()).thenReturn(mockedDate);

			Transation savedTransation = transationService.save(transation);

			assertAll("Transation", () -> assertEquals(1L, savedTransation.getId()), () -> {
				assertAll("Conta", () -> assertEquals("default", savedTransation.getAccount().getName()), () -> {
					assertAll("User",
							() -> assertEquals("default user", savedTransation.getAccount().getUser().getName()));
				});
			});
			ldt.verify(() -> LocalDateTime.now(), Mockito.atLeastOnce());
		}
	}

	@Test
	public void savingValidTransationNonStaticMock() {

		Transation transation = TransationBuilder.oneTransation().withID(null).now();

		when(dao.save(Mockito.any(Transation.class))).thenReturn(TransationBuilder.oneTransation().now());

		try (MockedConstruction<Date> ldt = Mockito.mockConstruction(Date.class, (mock, context) -> {
			when(mock.getHours()).thenReturn(2);
		}))

		{
			Transation savedTransation = transationService.save(transation);

			assertAll("Transation", () -> assertEquals(1L, savedTransation.getId()), () -> {
				assertAll("Conta", () -> assertEquals("default", savedTransation.getAccount().getName()), () -> {
					assertAll("User",
							() -> assertEquals("default user", savedTransation.getAccount().getUser().getName()));
				});
			});
			assertEquals(1, ldt.constructed().size());
		}
	}

	@Test
	public void savingValidTransationSpy() {

		Transation transation = TransationBuilder.oneTransation().withID(null).now();

		when(dao.save(Mockito.any(Transation.class))).thenReturn(TransationBuilder.oneTransation().now());
		when(transationService.getTime()).thenReturn(LocalDateTime.of(2023, 11, 16, 11, 6));

		Transation savedTransation = transationService.save(transation);

		assertAll("Transation", () -> assertEquals(1L, savedTransation.getId()), () -> {
			assertAll("Conta", () -> assertEquals("default", savedTransation.getAccount().getName()), () -> {
				assertAll("User", () -> assertEquals("default user", savedTransation.getAccount().getUser().getName()));
			});
		});
	}

	@Test
	public void transationOutOfOrderException() {
//		Mockito.reset(transationService);
		Transation transation = TransationBuilder.oneTransation().withID(null).now();

		when(transationService.getTime()).thenReturn(LocalDateTime.of(2023, 11, 16, 20, 6));

		ValidationException assertThrows2 = assertThrows(ValidationException.class,
				() -> transationService.save(transation));
		assertEquals("Out of order, try again", assertThrows2.getMessage());
	}

	@Test
	public void savingTransationWithoutStatus() {
		Transation transation = TransationBuilder.oneTransation().withStatus(null).now();
		transationService.save(transation);
		
		verify(dao).save(captor.capture());
		assertFalse(captor.getValue().isStatus());
	}

}
