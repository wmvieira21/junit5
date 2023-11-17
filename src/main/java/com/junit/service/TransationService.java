package com.junit.service;

import java.time.LocalDateTime;
import java.util.Date;

import com.junit.domain.Transation;
import com.junit.domain.exceptions.ValidationException;
import com.junit.sercive.repository.TransationDAO;

public class TransationService {

	private TransationDAO transationDAO;

	public Transation save(Transation transation) {
		// savingValidTransationNonStaticMock()
		if (new Date().getHours() > 19) {
			throw new ValidationException("Out of order, try again");
		}
		
		if (getTime().getHour() > 19) {
			throw new ValidationException("Out of order, try again");
		}

		if (transation.getDescription() == null)
			throw new ValidationException("Invalid Description");
		if (transation.getAccount() == null)
			throw new ValidationException("Invalid Account");
		if (transation.getDate() == null)
			throw new ValidationException("Invalid Date");
		if (transation.getValue() == null)
			throw new ValidationException("Invalid Value");
		if (transation.isStatus() == null)
			transation.setStatus(false);

		return transationDAO.save(transation);
	}

	public LocalDateTime getTime() {
		return LocalDateTime.now();
	}
}
