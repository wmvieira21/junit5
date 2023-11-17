package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.domain.Account;
import com.junit.domain.exceptions.ValidationException;
import com.junit.sercive.repository.AccountRepository;
import com.junit.service.AccountService;
import com.junit.service.external.AccountEvent;
import com.junit.service.external.AccountEvent.EventType;

import domains.builder.AccountBuilder;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private AccountEvent accountEvent;

	@InjectMocks
	private AccountService accountService;

	@Captor
	private ArgumentCaptor<Account> accountCaptor;

	@Test
	public void savingAccountSucefully() throws Exception {
		Account accountToSave = AccountBuilder.getInstanceAccount().withID(null).now();

		when(accountRepository.save(Mockito.any(Account.class))).thenReturn(AccountBuilder.getInstanceAccount().now());

		Mockito.doNothing().when(accountEvent).dispatch(accountToSave, EventType.CREATED);

		Account result = accountService.save(accountToSave);
		
		assertNotNull(result);

		verify(accountRepository).save(accountCaptor.capture());

		assertNull(accountCaptor.getValue().getId());
	}

	@Test
	public void rejectingDuplicatedNameAccount() {
		Account account = AccountBuilder.getInstanceAccount().withName("1212").now();

		when(accountRepository.getAccountsByUser(account.getUser()))
				.thenReturn(List.of(AccountBuilder.getInstanceAccount().withName("1212").now()));

		assertThrows(ValidationException.class, () -> accountService.save(account));
	}

	@Test
	public void savingAccountWithDifferentName() {
		Account account = AccountBuilder.getInstanceAccount().withName("1212").now();

		when(accountRepository.getAccountsByUser(account.getUser()))
				.thenReturn(List.of(AccountBuilder.getInstanceAccount().withName("conta no banco").now()));

		when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

		Account accountsaved = accountService.save(account);

		assertNotNull(accountsaved);
	}

	@Test
	public void rejectingAccountWithoutEvent() throws Exception {
		Account accountToSave = AccountBuilder.getInstanceAccount().now();

		when(accountRepository.save(Mockito.any(Account.class))).thenReturn(accountToSave);

		Mockito.doThrow(new Exception("Catastrophic Fail")).when(accountEvent).dispatch(accountToSave,
				EventType.CREATED);

		Exception assertThrows2 = assertThrows(Exception.class, () -> {
			accountService.save(accountToSave);
		});

		assertEquals("Fail as trying to dispatch an account", assertThrows2.getMessage());

		verify(accountRepository).delete(Mockito.any(Account.class));
	}
}
