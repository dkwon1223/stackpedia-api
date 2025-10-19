package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.exception.ValidationException;
import com.dkwondev.stackpedia_v2_api.model.dto.auth.LoginResponseDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.EmailVerificationToken;
import com.dkwondev.stackpedia_v2_api.model.entity.Role;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.model.mapper.UserMapper;
import com.dkwondev.stackpedia_v2_api.repository.EmailVerificationTokenRepository;
import com.dkwondev.stackpedia_v2_api.repository.RoleRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final EmailService emailService;

    @Transactional
    public User signup(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email address already in use");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ValidationException("Username already in use");
        }
        // Traditional signup requires a password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidationException("Password is required for traditional signup");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        user.setAuthorities(authorities);
        user.setEmailVerified(false);
        user.setEnabled(false); // Account disabled until email verified

        User savedUser = userRepository.save(user);

        // Create verification token
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = EmailVerificationToken.builder()
                .token(token)
                .user(savedUser)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .createdAt(LocalDateTime.now())
                .build();
        emailVerificationTokenRepository.save(verificationToken);

        // Send verification email
        emailService.sendVerificationEmail(savedUser.getEmail(), token);

        return savedUser;
    }

    @Transactional
    public void verifyEmail(String token) {
        EmailVerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ValidationException("Invalid verification token"));

        if (verificationToken.isExpired()) {
            throw new ValidationException("Verification token has expired");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        user.setEnabled(true);
        userRepository.save(user);

        emailVerificationTokenRepository.delete(verificationToken);
    }

    public LoginResponseDTO login(String username, String password) {

        try {
            log.info("Login attempt for username/email: {}", username);

            // Check if user exists and is OAuth-only (try username first, then email)
            User user = userRepository.findByUsername(username)
                    .or(() -> userRepository.findByEmail(username))
                    .orElseThrow(() -> {
                        log.warn("User not found: {}", username);
                        return new ValidationException("Invalid credentials");
                    });

            log.info("User found - userId: {}, username: {}, enabled: {}, emailVerified: {}, isOAuthOnly: {}",
                    user.getUserId(), user.getUsername(), user.isEnabled(),
                    user.getEmailVerified(), user.isOAuthOnly());

            if (user.isOAuthOnly()) {
                log.warn("OAuth-only account attempted password login: {}", username);
                throw new ValidationException("This account uses OAuth authentication. Please sign in with Google or GitHub.");
            }

            log.info("Attempting authentication with AuthenticationManager for user: {}", username);
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            log.info("Authentication successful for user: {}", username);
            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userMapper.userToUserDTO(user), token);

        } catch(AuthenticationException e) {
            log.error("Authentication failed for user: {} - Error: {}", username, e.getMessage());
            throw new ValidationException("Invalid credentials");
        }
    }
}
