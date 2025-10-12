package com.dkwondev.stackpedia_v2_api.oauth2;

import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUri;

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
        // Generate JWT token
        String token = tokenService.generateJwt(authentication);

        // Get user info
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = null;

        if (oAuth2User instanceof OAuth2UserPrincipal) {
            user = ((OAuth2UserPrincipal) oAuth2User).getUser();
        }

        // Store JWT token in HTTP-only cookie
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Only send over HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
        response.addCookie(jwtCookie);

        // Store user info in a separate cookie (non-sensitive, accessible to JS)
        if (user != null) {
            String userInfo = String.format("{\"userId\":\"%s\",\"username\":\"%s\",\"email\":\"%s\"}",
                user.getUserId(), user.getUsername(), user.getEmail());
            // URL-encode the JSON string to make it cookie-safe
            String encodedUserInfo = URLEncoder.encode(userInfo, StandardCharsets.UTF_8);
            Cookie userInfoCookie = new Cookie("userInfo", encodedUserInfo);
            userInfoCookie.setHttpOnly(false); // Allow JavaScript access for display
            userInfoCookie.setSecure(true);
            userInfoCookie.setPath("/");
            userInfoCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(userInfoCookie);
        }

        // Simple redirect without sensitive data in URL
        return redirectUri;
    }
}