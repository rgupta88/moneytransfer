package com.monerytransfer.dto;

import static java.math.RoundingMode.DOWN;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class MoneyTransferDto {

	@XmlElement(name = "fromAccountId")
	private String fromAccountId;
	@XmlElement(name = "toAccountId")
	private String toAccountId;
	@XmlElement(name = "amount")
	private String amount;

	@XmlElement(name = "transactionMsg")
	private String transactionMsg;

	public MoneyTransferDto(@JsonProperty("fromAccountId") String fromAccountId, @JsonProperty("toAccountId") String toAccountId,
			@JsonProperty("amount") String amount, @JsonProperty("transactionMsg") String transactionMsg) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.transactionMsg = transactionMsg;
	}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public String getAmount() {
		return amount;
	}

	public String getTransactionMsg() {
		return transactionMsg;
	}
	
	public void validate() {
        InputValidation.checkBlank(fromAccountId, "From account is empty");
        InputValidation.checkBlank(toAccountId, "To account is empty");
        InputValidation.checkBlank(amount, "Amount is empty");
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Accounts are equal");
        }
        BigDecimal amount = new BigDecimal(this.amount).setScale(2, DOWN);
		if (amount.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Amount is negative");
        }
    }

}
