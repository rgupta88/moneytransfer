package com.monerytransfer.resource;

import static io.restassured.RestAssured.given;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.monerytransfer.dto.AccountDto;
import com.monerytransfer.dto.DepositWithdrawDTO;
import com.monerytransfer.dto.MoneyTransferDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTransactionResource {

	 static TestServer server;

	@BeforeClass
	public static void setup() {
		RestAssured.port = Integer.valueOf(8090);
		RestAssured.baseURI = "http://localhost";
		server = new TestServer();
		server.start();
	}

	@Test
	public void testDeposit() {
		// Create account first
		AccountDto dto = new AccountDto("9999");
		given().contentType(ContentType.JSON).body(dto).post("/moneytransfer/api/account/create").then().statusCode(200)
				.extract().response();
		dto = new AccountDto("rgupta");
		given().contentType(ContentType.JSON).body(dto).post("/moneytransfer/api/account/create").then().statusCode(200)
				.extract().response();
		// test deposit
		DepositWithdrawDTO deposit = new DepositWithdrawDTO("1", "50000");
		given().contentType(ContentType.JSON).body(deposit).post("moneytransfer/api/transaction/deposit").then()
				.statusCode(200);
	}

	@Test
	public void testTransferAmount() {
		// test WithDraw
		MoneyTransferDto transfer = new MoneyTransferDto("1", "2", "2000", "Party Bill");
		for (int i = 0; i < 5; i++)
			given().contentType(ContentType.JSON).body(transfer).post("moneytransfer/api/transaction/transfer").then()
					.statusCode(200);
		transfer = new MoneyTransferDto("2", "1", "5000", "Party Bill");
		given().contentType(ContentType.JSON).body(transfer).post("moneytransfer/api/transaction/transfer").then()
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
		// test WithDraw negative case assume not to deposit more than
		// 9999999999999
		DepositWithdrawDTO deposit = new DepositWithdrawDTO("1", "9999999999999");
		given().contentType(ContentType.JSON).body(deposit).post("moneytransfer/api/transaction/withdraw").then()
				.statusCode(500);
	}

	@AfterClass
	public static void testStopJetty() {
		server.stop();
	}

}
