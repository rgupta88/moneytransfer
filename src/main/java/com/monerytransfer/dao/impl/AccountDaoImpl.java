package com.monerytransfer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.monerytransfer.dao.AccountDao;
import com.monerytransfer.dao.DBManager;
import com.monerytransfer.model.Account;

public class AccountDaoImpl implements AccountDao {

	private final DBManager dbManager;

	private final AtomicInteger accountNumber;

	public AccountDaoImpl(DBManager dbManager) {
		this.dbManager = dbManager;
		accountNumber = new AtomicInteger(1);
		createTable();
	}

	private void createTable() {
		try (Connection cn = dbManager.getConnection()) {
			PreparedStatement stmt = cn.prepareStatement(
					"CREATE TABLE accounts (account_number VARCHAR(50) PRIMARY KEY,user_id VARCHAR(50), balance number(20,0))");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void create(String userId) {
		// TODO Auto-generated method stub
		try (Connection cn = dbManager.getConnection()) {
			PreparedStatement stmt = cn.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?)");
			int count = 0;
			stmt.setString(++count, Integer.toString(accountNumber.getAndIncrement()));
			stmt.setString(++count, userId);
			stmt.setLong(++count, 0L);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Account find(String number) {
		// TODO Auto-generated method stub
		try (Connection cn = dbManager.getConnection()) {
			PreparedStatement stmt = cn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?");
			stmt.setString(1, number);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Account(rs.getString("account_number"), rs.getString("user_id"), rs.getString("balance"));
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		try (Connection cn = dbManager.getConnection()) {
			PreparedStatement stmt = cn.prepareStatement("SELECT * FROM accounts order by account_number");
			ResultSet rs = stmt.executeQuery();
			List<Account> accountList = new ArrayList<Account>();
			while (rs.next()) {
				accountList.add(
						new Account(rs.getString("account_number"), rs.getString("user_id"), rs.getString("balance")));
			}
			stmt.close();
			return accountList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
