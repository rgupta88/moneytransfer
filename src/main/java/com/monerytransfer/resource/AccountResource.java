package com.monerytransfer.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.monerytransfer.dao.AccountDao;
import com.monerytransfer.dto.AccountDetailDto;
import com.monerytransfer.dto.AccountDto;
import com.monerytransfer.dto.TransactionHistoryDto;
import com.monerytransfer.injection.ApplicationContext;
import com.monerytransfer.main.App;
import com.monerytransfer.model.Account;
import com.monerytransfer.model.TransactionHistory;
import com.monerytransfer.service.TransactionService;

@Path(RestConstants.account)
public class AccountResource {
	final static Logger logger = Logger.getLogger(AccountResource.class);
	private AccountDao dao = ApplicationContext.getAccountdao();
	private TransactionService service = ApplicationContext.getTransactionservice();

	@GET
	@Path(RestConstants.seperator + RestConstants.account_list)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AccountDetailDto> getAll() {
		List<Account> acoountList = dao.findAll();
		List<AccountDetailDto> acoountDtos = new ArrayList<AccountDetailDto>();
		acoountList.forEach(account -> {
			acoountDtos.add(new AccountDetailDto(account.getNumber(), account.getUserId(),
					account.getBalance().toString(), null));
		});
		return acoountDtos;
	}

	@GET
	@Path("/{accountNumber}/detail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountDetail(@PathParam("accountNumber") String accountNumber) {
		Account account = dao.find(accountNumber);
		List<TransactionHistory> hisrtoryList = service.getTransactionHistory(accountNumber);
		List<TransactionHistoryDto> hisrtoryDtoList = new ArrayList<TransactionHistoryDto>();
		hisrtoryList.forEach(history -> {
			hisrtoryDtoList.add(new TransactionHistoryDto(history.getTransactionId(), history.getAmount().toString(),
					history.getTransactionType(), history.getTransactionStatus(), history.getTransactionMsg(),
					history.getTransactionDate()));
		});
		logger.info("Account details for account number : " + accountNumber);

		return Response.ok(new AccountDetailDto(account.getNumber(), account.getUserId(),
				account.getBalance().toString(), hisrtoryDtoList), MediaType.APPLICATION_JSON).build();
	}
	/*
	 * 
	 * Create new account
	 * */

	@POST
	@Path(RestConstants.seperator + RestConstants.create_account)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(AccountDto account) {
		logger.info("Created accounted for user : " + account.getUserId());
		dao.create(account.getUserId());
		return Response.ok(new String("Account Created"), MediaType.APPLICATION_JSON).build();
	}
}
