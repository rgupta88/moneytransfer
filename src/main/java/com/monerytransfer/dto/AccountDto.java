package com.monerytransfer.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class AccountDto {
	
	@XmlElement(name = "userId")
	private String userId;

	public AccountDto(@JsonProperty("userId") String userId) {
		this.userId = userId;
	}
	

	public String getUserId() {
		return userId;
	}

}
