package com.monerytransfer.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.monerytransfer.dao.AccountDao;
import com.monerytransfer.dao.DBManager;
import com.monerytransfer.dao.TransactionDao;
import com.monerytransfer.injection.ApplicationContext;
import com.monerytransfer.model.Account;
import com.monerytransfer.model.TransactionHistory;
import com.monerytransfer.model.TransactionMessage;
import com.monerytransfer.model.TransactionStatus;
import com.monerytransfer.model.TransactionType;

public class TransactionDaoImpl implements TransactionDao {
	private final DBManager dbManager;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private final AccountDao accountDao = ApplicationContext.getAccountdao();

	public TransactionDaoImpl(DBManager dbManager) {
		this.dbManager = dbManager;
		createTable();
	}

	private void createTable() {
		try (Connection cn = dbManager.getConnection()) {
			PreparedStatement stmt = cn.prepareStatement(
					"CREATE TABLE transaction_history (transaction_history_id VARCHAR(50) PRIMARY KEY, transaction_id VARCHAR(50) , "
							+ " account_id VARCHAR(50), amount number(20,0) , transaction_type VARCHAR(10), "
							+ "transaction_status VARCHAR(10) , transaction_msg  VARCHAR(200), transaction_date VARCHAR(10))");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<TransactionHistory> getTransactionHistory(String accountNumber) {

		try (Connection cn = dbManager.getConnection()) {
			PreparedStatement stmt = cn.prepareStatement(
					"SELECT * from transaction_history Where account_id = ? order by transaction_date desc limit 100;");
			stmt.setString(1, accountNumber);
			ResultSet rs = stmt.executeQuery();
			List<TransactionHistory> historyList = new ArrayList<TransactionHistory>();
			while (rs.next()) {
				historyList.add(new TransactionHistory(rs.getString("transaction_id"), rs.getString("account_id"),
						new BigDecimal(rs.getString("amount")), rs.getString("transaction_type"),
						rs.getString("transaction_status"), rs.getString("transaction_msg"),
						rs.getString("transaction_date")));
			}
			stmt.close();
			return historyList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void createTransactionHistory(String fromAccount, String toAccount, String amount, String mesg) {
		// TODO Auto-generated method stub
		String transactionId = UUID.randomUUID().toString();
		createAccountHistory(transactionId, fromAccount, amount, mesg, TransactionType.Debit.toString(),
				TransactionStatus.SUCCESS.toString());
		createAccountHistory(transactionId, toAccount, amount, mesg, TransactionType.Credit.toString(),
				TransactionStatus.SUCCESS.toString());
	}

	private void createAccountHistory(String transactionId, String account, String amount, String mesg,
			String transactionType, String status) {
		try (Connection cn = dbManager.getConnection()) {
			PreparedStatement stmt = cn.prepareStatement("INSERT INTO transaction_history VALUES (?,?,?,?,?,?,?,?)");
			int count = 0;
			stmt.setString(++count, UUID.randomUUID().toString());
			stmt.setString(++count, transactionId);
			stmt.setString(++count, account);
			stmt.setString(++count, amount);
			stmt.setString(++count, transactionType);
			stmt.setString(++count, status);
			stmt.setString(++count, mesg);
			stmt.setString(++count, simpleDateFormat.format(new Date()));
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String transfer(String fromAccount, String toAccount, String amount, String mesg) {
		// TODO Auto-generated method stub
		if (fromAccount.compareTo(toAccount) > 0) {
			synchronized (fromAccount.intern()) {
				synchronized (toAccount.intern()) {
					try {
						doTransfer(fromAccount, toAccount, amount);
						createTransactionHistory(fromAccount, toAccount, amount, mesg);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			synchronized (toAccount.intern()) {
				synchronized (fromAccount.intern()) {
					try {
						doTransfer(fromAccount, toAccount, amount);
						createTransactionHistory(fromAccount, toAccount, amount, mesg);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return TransactionStatus.SUCCESS.toString();
	}

	private void doTransfer(String fromAccount, String toAccount, String amount) throws SQLException {
		Account debitAccount = accountDao.find(fromAccount);
		Account creditAccount = accountDao.find(toAccount);
		if (debitAccount != null && creditAccount != null) {
			BigDecimal transferAmount = new BigDecimal(amount);
			checkBalance(debitAccount, amount);
			debitAccount.debit(transferAmount);
			creditAccount.credit(transferAmount);
			Connection cn = dbManager.getConnection();
			PreparedStatement stmt = null;
			try {
				cn.setAutoCommit(false);
				stmt = cn.prepareStatement("update accounts set balance =? where account_number =?");
				stmt.setString(1, debitAccount.getBalance().toString());
				stmt.setString(2, debitAccount.getNumber());
				stmt.execute();
				stmt.setString(1, creditAccount.getBalance().toString());
				stmt.setString(2, creditAccount.getNumber());
				stmt.execute();
				cn.commit();
			} catch (Exception e) {
				cn.rollback();
				e.printStackTrace();
			} finally {
				if (stmt != null)
					stmt.close();
				if (cn != null)
					cn.close();
			}
		}

	}

	@Override
	public String withDraw(String accountNumber, String amount) {
		// TODO Auto-generated method stub
		Account debitAccount = accountDao.find(accountNumber);
		if (debitAccount != null) {
			checkBalance(debitAccount, amount);
			debitAccount.debit(new BigDecimal(amount));
			try (Connection cn = dbManager.getConnection()) {
				PreparedStatement stmt = cn.prepareStatement("update accounts set balance =? where account_number =?");
				int count = 0;
				stmt.setString(++count, debitAccount.getBalance().toString());
				stmt.setString(++count, accountNumber);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			createAccountHistory(UUID.randomUUID().toString(), accountNumber, amount, TransactionType.Debit.toString(),
					TransactionMessage.WITHDRAW.toString(), TransactionStatus.SUCCESS.toString());
			return TransactionMessage.WITHDRAW.toString();
		}
		return TransactionStatus.FAILED.toString();

	}

	private void checkBalance(Account debitAccount, String amount) {
		try {
			debitAccount.checkBalance(new BigDecimal(amount));
		} catch (RuntimeException e) {
			createAccountHistory(UUID.randomUUID().toString(), debitAccount.getNumber(), amount,
					TransactionType.Debit.toString(), TransactionType.Debit.toString(),
					TransactionStatus.FAILED.toString());
			throw e;
		}
	}

	@Override
	public String deposit(String accountNumber, String amount) {
		// TODO Auto-generated method stub
		Account creditAccount = accountDao.find(accountNumber);
		if (creditAccount != null) {
			creditAccount.credit(new BigDecimal(amount));
			try (Connection cn = dbManager.getConnection()) {
				PreparedStatement stmt = cn.prepareStatement("update accounts set balance =? where account_number =?");
				int count = 0;
				stmt.setString(++count, creditAccount.getBalance().toString());
				stmt.setString(++count, accountNumber);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			createAccountHistory(UUID.randomUUID().toString(), accountNumber, amount, TransactionType.Credit.toString(),
					TransactionMessage.DEPOSIT.toString(), TransactionStatus.SUCCESS.toString());
			return TransactionMessage.DEPOSIT.toString();
		}
		return TransactionStatus.FAILED.toString();
	}

}
