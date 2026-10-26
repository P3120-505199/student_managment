package ru.itmo.weblab3.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.weblab3.dto.ErrorResponse;
import ru.itmo.weblab3.exception.AccessDeniedException;

@Provider
public class AccessDeniedExceptionMapper
        implements ExceptionMapper<AccessDeniedException> {

    @Override
    public Response toResponse(AccessDeniedException exception) {
        ErrorResponse error = new ErrorResponse(
                403,
                "Forbidden",
                exception.getMessage()
        );

        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(error)
                .build();
    }
}
