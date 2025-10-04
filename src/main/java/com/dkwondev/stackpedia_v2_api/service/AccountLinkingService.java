package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.exception.ValidationException;
import com.dkwondev.stackpedia_v2_api.model.entity.AuthProvider;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.model.entity.UserAuthProvider;
import com.dkwondev.stackpedia_v2_api.repository.UserAuthProviderRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountLinkingService {

    private final UserRepository userRepository;
    private final UserAuthProviderRepository userAuthProviderRepository;

    @Transactional
    public void linkOAuthProvider(Long userId, AuthProvider provider, String providerUserId, String providerEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found"));

        // Check if this provider is already linked to this user
        if (userAuthProviderRepository.existsByUserUserIdAndProvider(userId, provider)) {
            throw new ValidationException("This " + provider.name() + " account is already linked");
        }

        // Check if this provider account is linked to a different user
        userAuthProviderRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .ifPresent(existingProvider -> {
                    if (!existingProvider.getUser().getUserId().equals(userId)) {
                        throw new ValidationException("This " + provider.name() + " account is already linked to another user");
                    }
                });

        // Verify email matches if user has email verification enabled
        if (user.getEmailVerified() && !user.getEmail().equals(providerEmail)) {
            throw new ValidationException("OAuth email must match your account email");
        }

        // Create the link
        UserAuthProvider authProvider = UserAuthProvider.builder()
                .user(user)
                .provider(provider)
                .providerUserId(providerUserId)
                .providerEmail(providerEmail)
                .build();

        userAuthProviderRepository.save(authProvider);
    }

    @Transactional
    public void unlinkOAuthProvider(Long userId, AuthProvider provider) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found"));

        // Prevent unlinking if it's the only auth method and user has no password
        if (user.isOAuthOnly()) {
            long linkedProviders = user.getAuthProviders().size();
            if (linkedProviders <= 1) {
                throw new ValidationException("Cannot unlink. You must have at least one authentication method. Please set a password first.");
            }
        }

        UserAuthProvider authProvider = userAuthProviderRepository
                .findByUserUserIdAndProvider(userId, provider)
                .orElseThrow(() -> new ValidationException(provider.name() + " is not linked to your account"));

        userAuthProviderRepository.delete(authProvider);
    }

    public List<String> getLinkedProviders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found"));

        return user.getAuthProviders().stream()
                .map(ap -> ap.getProvider().name().toLowerCase())
                .collect(Collectors.toList());
    }
}