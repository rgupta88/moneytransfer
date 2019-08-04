package com.monerytransfer.model;

import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "transaction_history")
public class TransactionHistory {

	@DatabaseField(id = true, columnName = "transaction_history_id")
	private String transaction_history_id;
	
	@DatabaseField(columnName = "transaction_id")
	private String transactionId;

	@DatabaseField(columnName = "account_id")
	private String accountId;

	@DatabaseField(columnName = "amount")
	private BigDecimal amount;
	@DatabaseField(columnName = "transaction_type")
	private String transactionType;
	@DatabaseField(columnName = "transaction_status")
	private String transactionStatus;
	@DatabaseField(columnName = "transaction_msg")
	private String transactionMsg;
	@DatabaseField(columnName = "transaction_date")
	private String transactionDate;

	public TransactionHistory(String transactionId,String accountId, BigDecimal amount, String transactionType, String transactionStatus,
			String transactionMsg, String transactionDate) {
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.amount = amount;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.transactionMsg = transactionMsg;
		this.transactionDate = transactionDate;

	}

	public String getAccountId() {
		return accountId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public String getTransactionMsg() {
		return transactionMsg;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

}
