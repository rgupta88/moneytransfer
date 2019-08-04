package com.monerytransfer.dto;

import static java.math.RoundingMode.DOWN;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class DepositWithdrawDTO {
	@XmlElement(name = "accountNumber")
	private String accountNumber;

	@XmlElement(name = "amount")
	private String amount;

	public DepositWithdrawDTO(@JsonProperty("accountNumber") String accountNumber,
			@JsonProperty("amount") String amount) {
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAmount() {
		return amount;
	}
	
	public void validate(){
		BigDecimal amount = new BigDecimal(this.amount).setScale(2, DOWN);
		if (amount.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Amount is negative");
        }
		 InputValidation.checkBlank(accountNumber, "Account number is empty");
	}

}
