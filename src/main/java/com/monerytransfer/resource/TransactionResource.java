package com.monerytransfer.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.monerytransfer.dto.DepositWithdrawDTO;
import com.monerytransfer.dto.MoneyTransferDto;
import com.monerytransfer.dto.TransactionHistoryDto;
import com.monerytransfer.exceptions.ExceptionHandler;
import com.monerytransfer.injection.ApplicationContext;
import com.monerytransfer.model.TransactionHistory;
import com.monerytransfer.service.TransactionService;

@Path(RestConstants.transaction)
public class TransactionResource implements ExceptionHandler {

	private TransactionService service = ApplicationContext.getTransactionservice();

	@POST
	@Path(RestConstants.seperator + RestConstants.transfer)
	@Produces(MediaType.TEXT_PLAIN)
	public String doTransfer(MoneyTransferDto transferDto) {
		transferDto.validate();
		return service.doTransfer(transferDto.getFromAccountId(), transferDto.getToAccountId(), transferDto.getAmount(),
				transferDto.getTransactionMsg());
	}

	@GET
	@Path("/{accountNumber}/history")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TransactionHistoryDto> getTransactionHistory(@PathParam("accountNumber") String accountNumber) {
		List<TransactionHistory> historyList = service.getTransactionHistory(accountNumber);
		List<TransactionHistoryDto> historyDto = new ArrayList<TransactionHistoryDto>();
		historyList.forEach(history -> {
			historyDto.add(new TransactionHistoryDto(history.getTransactionId(), history.getAmount().toString(),
					history.getTransactionType(), history.getTransactionStatus(), history.getTransactionMsg(),
					history.getTransactionDate()));
		});
		return historyDto;
	}

	@POST
	@Path(RestConstants.seperator + RestConstants.withdraw)
	@Produces(MediaType.TEXT_PLAIN)
	public Response doWithDraw(DepositWithdrawDTO depositWithdrawDTO) {
		try {
			return Response
					.ok(service.doWithdraw(depositWithdrawDTO.getAccountNumber(), depositWithdrawDTO.getAmount()),
							MediaType.APPLICATION_JSON)
					.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
		}

	}

	@POST
	@Path(RestConstants.seperator + RestConstants.deposit)
	@Produces(MediaType.TEXT_PLAIN)
	public String doDeposit(DepositWithdrawDTO depositWithdrawDTO) {
		return service.doDeposit(depositWithdrawDTO.getAccountNumber(), depositWithdrawDTO.getAmount());
	}
}
