package com.dkwondev.stackpedia_v2_api.oauth2;

import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUri;

    @Value("${app.oauth2.cookie-secure:true}")
    private boolean cookieSecure;

    @Value("${app.oauth2.cookie-domain:#{null}}")
    private String cookieDomain;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect.");
            return;
        }

        String targetUrl = determineTargetUrl(request, response, authentication);

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        log.info("OAuth2 authentication success - preparing cookies and redirect");

        // Generate JWT token
        String token = tokenService.generateJwt(authentication);

        // Get user info
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = null;

        if (oAuth2User instanceof OAuth2UserPrincipal) {
            user = ((OAuth2UserPrincipal) oAuth2User).getUser();
            log.info("OAuth2 user authenticated: userId={}, username={}, email={}",
                    user.getUserId(), user.getUsername(), user.getEmail());
        }

        // Add SameSite attribute via response header (Spring Boot doesn't support SameSite directly on Cookie)
        // Note: When cookieSecure is false (development), we use SameSite=Lax instead of None
        // SameSite=None requires Secure flag, which doesn't work with localhost HTTP
        String sameSite = cookieSecure ? "None" : "Lax";

        // Build domain attribute if configured
        String domainAttr = (cookieDomain != null && !cookieDomain.isEmpty())
            ? String.format("Domain=%s; ", cookieDomain)
            : "";

        response.addHeader("Set-Cookie", String.format(
            "jwt=%s; %sPath=/; Max-Age=%d; HttpOnly; %sSameSite=%s",
            token, domainAttr, 24 * 60 * 60, cookieSecure ? "Secure; " : "", sameSite
        ));

        log.info("JWT cookie created: secure={}, sameSite={}, domain={}", cookieSecure, sameSite, cookieDomain);

        // Store user info in a separate cookie (non-sensitive, accessible to JS)
        if (user != null) {
            String userInfo = String.format("{\"userId\":\"%s\",\"username\":\"%s\",\"email\":\"%s\"}",
                user.getUserId(), user.getUsername(), user.getEmail());
            // URL-encode the JSON string to make it cookie-safe
            String encodedUserInfo = URLEncoder.encode(userInfo, StandardCharsets.UTF_8);

            // Add userInfo cookie with SameSite attribute
            response.addHeader("Set-Cookie", String.format(
                "userInfo=%s; %sPath=/; Max-Age=%d; %sSameSite=%s",
                encodedUserInfo, domainAttr, 24 * 60 * 60, cookieSecure ? "Secure; " : "", sameSite
            ));

            log.info("UserInfo cookie created with encoded data, domain={}", cookieDomain);
        }

        log.info("Redirecting to: {}", redirectUri);

        // Simple redirect without sensitive data in URL
        return redirectUri;
    }
}