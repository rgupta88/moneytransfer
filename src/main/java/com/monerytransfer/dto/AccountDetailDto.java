package com.monerytransfer.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class AccountDetailDto {

	@XmlElement(name = "accountNumber")
	private String accountNumber;

	@XmlElement(name = "userId")
	private String userId;

	@XmlElement(name = "balance")
	private String balance;
	@XmlElement(name = "transactions")
	List<TransactionHistoryDto> transactions;

	public AccountDetailDto(@JsonProperty("accountNumber") String accountNumber, @JsonProperty("userId") String userId,
			@JsonProperty("balance") String balance,
			@JsonProperty("transactions") List<TransactionHistoryDto> transactions) {
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.balance = balance;
		this.transactions = transactions;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getUserId() {
		return userId;
	}

	public String getBalance() {
		return balance;
	}

	public List<TransactionHistoryDto> getTransactions() {
		return transactions;
	}

}
