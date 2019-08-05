package com.monerytransfer.resource;

import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;

import com.monerytransfer.dto.AccountDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TestAccountResource {

	@BeforeClass
	public static void setup() {
		RestAssured.port = Integer.valueOf(8090);
		RestAssured.baseURI = "http://localhost";

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
}
