package com.monerytransfer.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class TransactionHistoryDto {

	@XmlElement(name = "transactionId")
	private String transactionId;

	@XmlElement(name = "amount")
	private String amount;
	@XmlElement(name = "transactionType")
	private String transactionType;
	@XmlElement(name = "transactionStatus")
	private String transactionStatus;
	@XmlElement(name = "transactionMsg")
	private String transactionMsg;
	@XmlElement(name = "transactionDate")
	private String transactionDate;

	public TransactionHistoryDto(@JsonProperty("transactionId") String transactionId,
			@JsonProperty("amount") String amount, @JsonProperty("transactionType") String transactionType,
			@JsonProperty("transactionStatus") String transactionStatus,
			@JsonProperty("transactionMsg") String transactionMsg,
			@JsonProperty("transactionDate") String transactionDate) {
		this.transactionId = transactionId;
		this.amount = amount;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.transactionMsg = transactionMsg;
		this.transactionDate = transactionDate;

	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getAmount() {
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
