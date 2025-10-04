package com.dkwondev.stackpedia_v2_api.oauth2;

import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

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

        // Build redirect URL with token
        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .queryParam("userId", user != null ? user.getUserId() : "")
                .queryParam("username", user != null ? user.getUsername() : "")
                .queryParam("email", user != null ? user.getEmail() : "")
                .build()
                .toUriString();
    }
}