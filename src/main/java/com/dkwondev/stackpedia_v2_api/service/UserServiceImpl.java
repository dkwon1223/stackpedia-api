package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.exception.ValidationException;
import com.dkwondev.stackpedia_v2_api.model.dto.LoginResponseDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Role;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.repository.RoleRepository;
import com.dkwondev.stackpedia_v2_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
