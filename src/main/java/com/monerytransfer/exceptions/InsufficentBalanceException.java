package com.monerytransfer.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class InsufficentBalanceException implements ExceptionMapper<RuntimeException> {

	public Response toResponse(RuntimeException e) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}
}