package com.junit.service.external;

import com.junit.domain.Account;

public interface AccountEvent {

	public enum EventType {
		CREATED, UPDATED, DELETED
	};

	void dispatch(Account account, EventType type) throws Exception;

}
