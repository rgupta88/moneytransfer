package com.monerytransfer.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.monerytransfer.dao.TransactionDao;
import com.monerytransfer.injection.ApplicationContext;
import com.monerytransfer.model.Account;
import com.monerytransfer.model.TransactionHistory;
import com.monerytransfer.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

	private TransactionDao transactionDao = ApplicationContext.getTransactiondao();

	public TransactionServiceImpl(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	@Override
	public List<TransactionHistory> getTransactionHistory(String accountNumber) {
		// TODO Auto-generated method stub
		return transactionDao.getTransactionHistory(accountNumber);
	}

	@Override
	public String doTransfer(String fromAccount, String toAccount, String amount, String mesg) {
		// TODO Auto-generated method stub
		return transactionDao.transfer(fromAccount, toAccount, amount, mesg);
	}

	@Override
	public String doDeposit(String account, String amount) {
		// TODO Auto-generated method stub
		return transactionDao.deposit(account, amount);
	}

	@Override
	public String doWithdraw(String account, String amount) {
		// TODO Auto-generated method stub
		return transactionDao.withDraw(account, amount);
	}
}
