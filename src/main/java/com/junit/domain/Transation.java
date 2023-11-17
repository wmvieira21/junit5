package com.junit.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transation {
	
	private Long id;
	private String description;
	private Double value;
	private Account account;
	private LocalDateTime date;
	private Boolean status;

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Double getValue() {
		return value;
	}

	public Account getAccount() {
		return account;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account, date, description, id, status, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transation other = (Transation) obj;
		return Objects.equals(account, other.account) && Objects.equals(date, other.date)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& status == other.status && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "Transation [id=" + id + ", description=" + description + ", value=" + value + ", account=" + account
				+ ", date=" + date + ", status=" + status + "]";
	}	
}
