package br.org.soujava.api.mapper;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.org.soujava.api.json.JsonError;
import br.org.soujava.api.json.JsonMessage;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		
		StringBuilder stringBuilder = new StringBuilder("Validation error: ");
		
		exception.getConstraintViolations().stream().forEach(e -> stringBuilder.append(e.getMessage()).append(", "));
		stringBuilder.replace(stringBuilder.lastIndexOf(", "), stringBuilder.length(), "");

		return Response.status(400)
				.entity(JsonMessage.builder().data(JsonError.builder().message(stringBuilder.toString()).build()))
				.build();
	}
}