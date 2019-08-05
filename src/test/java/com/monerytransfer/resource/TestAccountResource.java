package com.monerytransfer.resource;

import static io.restassured.RestAssured.given;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.monerytransfer.dto.AccountDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAccountResource {

	static TestServer server;

	@BeforeClass
	public static void setup() {
		RestAssured.port = Integer.valueOf(8090);
		RestAssured.baseURI = "http://localhost";
		server = new TestServer();
		server.start();
	}

	@Test
	public void testAccountCreate() {
		AccountDto dto = new AccountDto("gupta");
		given().contentType(ContentType.JSON).body(dto).post("/moneytransfer/api/account/create").then().statusCode(200)
				.extract().response();

	}

	@Test
	public void testAccountdetail() {

		given().when().get("/moneytransfer/api/account/1/detail").then().statusCode(200);
	}

	@AfterClass
	public static void testStopJetty() {
		server.stop();
	}

}
