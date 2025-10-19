package com.dkwondev.stackpedia_v2_api.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // Log the full error details for debugging
        log.error("OAuth2 authentication failed", exception);

        String errorMessage = "Authentication failed";

        // Extract more specific error message if available
        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2AuthenticationException oauth2Exception = (OAuth2AuthenticationException) exception;
            errorMessage = oauth2Exception.getError().getDescription();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = oauth2Exception.getError().getErrorCode();
            }
            log.error("OAuth2 Error Code: {}", oauth2Exception.getError().getErrorCode());
            log.error("OAuth2 Error Description: {}", oauth2Exception.getError().getDescription());
        }

        // If the error is from our custom validation (e.g., "Default role not found", "Email not found from OAuth2 provider")
        if (exception.getCause() != null) {
            log.error("Root cause: {}", exception.getCause().getMessage(), exception.getCause());
            errorMessage = exception.getCause().getMessage();
        }

        // Build the error redirect URL
        String targetUrl = buildErrorRedirectUrl(errorMessage);

        log.info("Redirecting to error page: {}", targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String buildErrorRedirectUrl(String errorMessage) {
        // Encode the error message to be URL-safe
        String encodedError = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

        // Redirect to frontend with descriptive error message
        // Format: http://localhost:5173/login?error=<encoded_message>
        return redirectUri + "?error=" + encodedError;
    }
}
