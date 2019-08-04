package com.monerytransfer.resource.test;

import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;

import com.monerytransfer.dto.AccountDto;
import com.monerytransfer.dto.DepositWithdrawDTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TestTransactionResource {

	@BeforeClass
	public static void setup() {
		RestAssured.port = Integer.valueOf(8090);
		RestAssured.baseURI = "http://localhost";

	}

	@Test
	public void testDeposit() {
		// Create account first
		AccountDto dto = new AccountDto("gupta");
		given().contentType(ContentType.JSON).body(dto).post("/moneytransfer/api/account/create").then().statusCode(200)
				.extract().response();
		// test deposit
		DepositWithdrawDTO deposit = new DepositWithdrawDTO("1", "5000");
		given().contentType(ContentType.JSON).body(deposit).post("moneytransfer/api/transaction/deposit").then()
				.statusCode(200);
	}
	
	@Test
	public void testWithDraw() {
		// test WithDraw
		DepositWithdrawDTO deposit = new DepositWithdrawDTO("1", "2000");
		given().contentType(ContentType.JSON).body(deposit).post("moneytransfer/api/transaction/withdraw").then()
				.statusCode(200);
	}
	
	@Test
	public void testWithDrawInsufficentBalace() {
		// test WithDraw negative case
		DepositWithdrawDTO deposit = new DepositWithdrawDTO("1", "50000");
		given().contentType(ContentType.JSON).body(deposit).post("moneytransfer/api/transaction/withdraw").then()
				.statusCode(409);
	}
	
	
}
