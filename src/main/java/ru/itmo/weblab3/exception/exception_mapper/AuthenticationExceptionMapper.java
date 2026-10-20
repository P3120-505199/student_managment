package ru.itmo.weblab3.exception.mapper;
import ru.itmo.weblab3.exception.AuthenticationException;                  
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;
import java.util.Map;
@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    @Override
    public Response toResponse(AuthenticationException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", 401,
                "error", "Unauthorized",
                "message", ex.getMessage()
        );
        return Response.status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Bearer")
                .entity(body)
                .build();
    }
}