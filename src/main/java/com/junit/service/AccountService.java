package com.junit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.junit.domain.Account;
import com.junit.domain.exceptions.ValidationException;
import com.junit.sercive.repository.AccountRepository;
import com.junit.service.external.AccountEvent;
import com.junit.service.external.AccountEvent.EventType;

public class AccountService {

	private AccountRepository accountRepository;
	private AccountEvent accountEvent;

	public AccountService(AccountRepository accountRepository, AccountEvent accountEvent) {
		super();
		this.accountRepository = accountRepository;
		this.accountEvent = accountEvent;
	}

	public Account save(Account account) {
		this.getAccountsByUser(account);

		Account perssistedAccount = new Account(account.getId(), account.getName() + LocalDate.now(),
				account.getUser());

		Account save = accountRepository.save(perssistedAccount);
		try {
			accountEvent.dispatch(perssistedAccount, EventType.CREATED);
		} catch (Exception e) {
			accountRepository.delete(account);
			throw new RuntimeException("Fail as trying to dispatch an account");
		}

		return save;
	}

	public Account getAccountsByUser(Account account) {
		List<Account> accountList = accountRepository.getAccountsByUser(account.getUser());

		accountList.stream().filter(c -> c.getName().equalsIgnoreCase(account.getName())).findFirst().ifPresent(c -> {
			throw new ValidationException(String.format("Account name:%s already exist for the user: %s",
					account.getName(), account.getUser().getName()));
		});

		return account;
	}
}
