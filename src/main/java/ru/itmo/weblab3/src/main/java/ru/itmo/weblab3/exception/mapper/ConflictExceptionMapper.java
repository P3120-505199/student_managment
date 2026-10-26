package ru.itmo.weblab3.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.weblab3.dto.ErrorResponse;
import ru.itmo.weblab3.exception.ConflictException;

@Provider
public class ConflictExceptionMapper
        implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException exception) {
        ErrorResponse error = new ErrorResponse(
                409,
                "Conflict",
                exception.getMessage()
        );

        return Response
                .status(Response.Status.CONFLICT)
                .entity(error)
                .build();
    }
}