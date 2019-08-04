package com.monerytransfer.resource.test;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.monerytransfer.dto.AccountDto;
import com.monerytransfer.resource.AccountResource;

public class TestAccountResource extends JerseyTest {

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(AccountResource.class);
	}

	@Test
	public void testUserFetchesSuccess() {
		AccountDto dto = new AccountDto( "rgupta");
		//Response output = target("api/account/create").request().post(Entity.entity(dto, MediaType.APPLICATION_JSON));
		//System.out.println(output.getStatus());
		//assertEquals("Http Response should be 200 ", 200, output.getStatus());
	}
}
