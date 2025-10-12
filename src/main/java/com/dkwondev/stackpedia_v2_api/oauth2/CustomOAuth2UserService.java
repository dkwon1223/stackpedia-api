package com.dkwondev.stackpedia_v2_api.oauth2;

import com.dkwondev.stackpedia_v2_api.exception.ValidationException;
import com.dkwondev.stackpedia_v2_api.model.entity.AuthProvider;
import com.dkwondev.stackpedia_v2_api.model.entity.Role;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.model.entity.UserAuthProvider;
import com.dkwondev.stackpedia_v2_api.repository.RoleRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserAuthProviderRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserAuthProviderRepository userAuthProviderRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException(ex.getMessage());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        if (userInfo.getEmail() == null || userInfo.getEmail().isEmpty()) {
            throw new ValidationException("Email not found from OAuth2 provider");
        }

        // Check if user already linked this provider
        Optional<UserAuthProvider> existingAuthProvider = userAuthProviderRepository
                .findByProviderAndProviderUserId(provider, userInfo.getId());

        User user;
        if (existingAuthProvider.isPresent()) {
            // User already linked with this provider
            user = existingAuthProvider.get().getUser();
            // Eagerly fetch authorities to avoid LazyInitializationException
            user.getAuthorities().size();
        } else {
            // Check if user exists with this email
            Optional<User> existingUser = userRepository.findByEmail(userInfo.getEmail());

            if (existingUser.isPresent()) {
                // User exists - link the OAuth provider to existing account
                user = existingUser.get();
                user.setEmailVerified(true); // OAuth provider verified the email
                user.setEnabled(true);
                linkAuthProvider(user, provider, userInfo);
                // Eagerly fetch authorities to avoid LazyInitializationException
                user.getAuthorities().size();
            } else {
                // Create new user
                user = createNewUser(provider, userInfo);
                // Authorities are already loaded for newly created users
            }
        }

        return new OAuth2UserPrincipal(user, oAuth2User.getAttributes());
    }

    private User createNewUser(AuthProvider provider, OAuth2UserInfo userInfo) {
        Role userRole = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new ValidationException("Default role not found"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        // Generate username from email or name
        String username = generateUsername(userInfo.getEmail(), userInfo.getName());

        User user = User.builder()
                .username(username)
                .email(userInfo.getEmail())
                .password(null) // OAuth users don't have passwords
                .authorities(authorities)
                .emailVerified(true) // OAuth provider verified the email
                .enabled(true)
                .build();

        user = userRepository.save(user);

        linkAuthProvider(user, provider, userInfo);

        return user;
    }

    private void linkAuthProvider(User user, AuthProvider provider, OAuth2UserInfo userInfo) {
        UserAuthProvider authProvider = UserAuthProvider.builder()
                .user(user)
                .provider(provider)
                .providerUserId(userInfo.getId())
                .providerEmail(userInfo.getEmail())
                .build();

        userAuthProviderRepository.save(authProvider);
    }

    private String generateUsername(String email, String name) {
        String baseUsername = name != null ? name.replaceAll("\\s+", "").toLowerCase()
                                           : email.split("@")[0];

        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }

        return username;
    }
}