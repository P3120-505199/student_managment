package ru.itmo.weblab3.exception.mapper;

import ru.itmo.weblab3.exception.AccessDeniedException;         
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;
import java.util.Map;
@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Override
    public Response toResponse(AccessDeniedException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", 403,
                "error", "Forbidden",
                "message", ex.getMessage()
        );
        return Response.status(Response.Status.FORBIDDEN).entity(body).build();
    }
}