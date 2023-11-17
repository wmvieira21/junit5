package com.junit.sercive.repository;

import java.util.List;
import java.util.Optional;

import com.junit.domain.Account;
import com.junit.domain.User;

public interface AccountRepository {
	
	Account save(Account account);
	
	List<Account> getAccountsByUser(User user);
	
	void delete(Account account);
}
