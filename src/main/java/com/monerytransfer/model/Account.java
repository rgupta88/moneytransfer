package com.monerytransfer.model;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.DOWN;

import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class Account {


	@DatabaseField(id = true, columnName = "acount_number")
	private String accountNumber;
	@DatabaseField( columnName = "user_id")
	private String userId;

	@DatabaseField(columnName = "balance")
	private BigDecimal balance;

	

	public Account(String accountNumber, String userId, String balance) {
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.balance = new BigDecimal(balance).setScale(2, DOWN);
	}
	

	public BigDecimal getBalance() {
		return balance != null ? balance : ZERO;
	}
	
	public String getUserId() {
		return userId;
	}

	public String getNumber() {
		return accountNumber;
	}
	
	public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        balance = balance == null ? amount : balance.add(amount);
    }
	
	public boolean greater(Account account) {
		return accountNumber.compareTo(account.accountNumber) > 0;
	}

	public void checkBalance(BigDecimal amount) {
		if (balance.compareTo(amount) < 0) {
			throw new RuntimeException(
					format("Insufficient balance (%s) for account:%s, required %s", balance, accountNumber, amount));
		}
	}


	

}
