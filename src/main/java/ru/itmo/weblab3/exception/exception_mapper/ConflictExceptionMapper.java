package ru.itmo.weblab3.exception.mapper;

import ru.itmo.weblab3.exception.ConflictException;           
import ru.itmo.weblab3.common.dto.ErrorResponse;               

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", 409,
                "error", "Conflict",
                "message", ex.getMessage()
        );
        return Response.status(Response.Status.CONFLICT).entity(body).build();
    }
}