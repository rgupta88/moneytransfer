package com.monerytransfer.dao;

import java.util.List;

import com.monerytransfer.model.TransactionHistory;

public interface TransactionDao {

	public String transfer(String fromAccount, String toAccount, String amount, String mesg);


	public String withDraw(String accountNumber, String amount);
	
	public String deposit(String accountNumber, String amount);

	public List<TransactionHistory> getTransactionHistory(String accountNumber);
}
