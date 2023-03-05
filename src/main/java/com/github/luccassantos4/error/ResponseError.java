package com.github.luccassantos4.error;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ResponseError implements ExceptionMapper<ConstraintViolationException >{

	@Override
	public Response toResponse(ConstraintViolationException  exception) {

		FieldError productException = new FieldError();

		productException.setMessage(exception.getConstraintViolations().stream().
				map(ConstraintViolation::getMessage)
				.collect(Collectors.toList()).toString());

		return Response.status(Response.Status.BAD_REQUEST).entity(productException).build();
	}
}
