package com.monerytransfer.injection;

import com.monerytransfer.dao.AccountDao;
import com.monerytransfer.dao.DBManager;
import com.monerytransfer.dao.TransactionDao;
import com.monerytransfer.dao.impl.AccountDaoImpl;
import com.monerytransfer.dao.impl.DBManagerImpl;
import com.monerytransfer.dao.impl.TransactionDaoImpl;
import com.monerytransfer.service.TransactionService;
import com.monerytransfer.service.impl.TransactionServiceImpl;

public class ApplicationContext {

	private static final AccountDao accountDao;

	private static final TransactionDao transactionDao;

	private static final TransactionService transactionService;

	static {
		DBManager dbManager = new DBManagerImpl();
		accountDao = new AccountDaoImpl(dbManager);
		transactionDao = new TransactionDaoImpl(dbManager);
		transactionService = new TransactionServiceImpl(transactionDao);
	}

	public static TransactionDao getTransactiondao() {
		return transactionDao;
	}

	public static AccountDao getAccountdao() {
		return accountDao;
	}

	public static TransactionService getTransactionservice() {
		return transactionService;
	}

}
