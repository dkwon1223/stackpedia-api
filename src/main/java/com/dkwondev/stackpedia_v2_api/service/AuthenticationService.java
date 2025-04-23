package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.exception.ValidationException;
import com.dkwondev.stackpedia_v2_api.model.dto.auth.LoginResponseDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Role;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.model.mapper.UserMapper;
import com.dkwondev.stackpedia_v2_api.repository.RoleRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public User signup(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email address already in use");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ValidationException("Username already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    public LoginResponseDTO login(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userMapper.userToUserDTO(userRepository.findByUsername(username).get()), token);

        } catch(AuthenticationException e) {
            throw e;
        }
    }
}
