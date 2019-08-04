package com.monerytransfer.dao;

import java.util.List;

import com.monerytransfer.model.Account;

public interface AccountDao {

	public void create(String userId);

	public Account find(String number);
	public List<Account> findAll();
}
