package com.monerytransfer.service;

import java.util.List;

import com.monerytransfer.model.TransactionHistory;

public interface TransactionService {

	public List<TransactionHistory> getTransactionHistory(String accountNumber);

	public String doTransfer(String fromAccount, String toAccount, String amount, String mesg);

	public String doDeposit(String accountNumber, String amount);

	public String doWithdraw(String accountNumber, String amount);

}
