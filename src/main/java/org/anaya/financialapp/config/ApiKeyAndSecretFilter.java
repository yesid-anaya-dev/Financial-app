package org.anaya.financialapp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAndSecretFilter extends OncePerRequestFilter {

    @Value("${security.api.key}")
    private String apiKey;

    @Value("${security.api.secret}")
    private String apiSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(!request.getRequestURI().contains("/api/v1")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestApiKey = request.getHeader( "api-key");
        String requestApiSecret = request.getHeader("api-secret");

        if (apiKey.equals(requestApiKey) && apiSecret.equals(requestApiSecret)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid API Key or Secret");
        }
    }
}
