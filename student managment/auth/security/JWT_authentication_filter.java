package ru.itmo.weblab3.security;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.weblab3.exception.AuthenticationException;
import ru.itmo.weblab3.service.JwtService;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Inject
    JwtService jwtService;

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext ctx) {
        if ("OPTIONS".equalsIgnoreCase(ctx.getMethod())) {
            return;
        }
        if (isPublic()) {
            return;
        }
        String header = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            throw new AuthenticationException("Missing or malformed Authorization header");
        }
        String token = header.substring(BEARER_PREFIX.length()).trim();
        JwtPayload payload = jwtService.parseAccess(token);
        boolean secure = ctx.getSecurityContext() != null
                && ctx.getSecurityContext().isSecure();
        ctx.setSecurityContext(new JwtSecurityContext(payload, secure));
    }

    private boolean isPublic() {
        if (resourceInfo != null) {
            if (resourceInfo.getResourceMethod() != null
                    && resourceInfo.getResourceMethod().isAnnotationPresent(Public.class)) {
                return true;
            }
            if (resourceInfo.getResourceClass() != null
                    && resourceInfo.getResourceClass().isAnnotationPresent(Public.class)) {
                return true;
            }
        }
        return false;
    }
}