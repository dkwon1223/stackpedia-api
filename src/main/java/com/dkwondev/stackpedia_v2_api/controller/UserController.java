package com.dkwondev.stackpedia_v2_api.controller;

import com.dkwondev.stackpedia_v2_api.model.dto.auth.LinkAccountResponseDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.auth.LoginRequestDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.auth.LoginResponseDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.auth.UserDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.AuthProvider;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.model.mapper.UserMapper;
import com.dkwondev.stackpedia_v2_api.service.AccountLinkingService;
import com.dkwondev.stackpedia_v2_api.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private final AccountLinkingService accountLinkingService;
    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody User user) {
        User newUser = authenticationService.signup(user);
        return new ResponseEntity<>(userMapper.userToUserDTO(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO body) {
        LoginResponseDTO loginResponse = authenticationService.login(body.getUsernameOrEmail(), body.getPassword());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        authenticationService.verifyEmail(token);
        return new ResponseEntity<>("Email verified successfully. You can now log in.", HttpStatus.OK);
    }

    // Note: Actual OAuth linking is handled via OAuth2 flow
    // These endpoints are for managing already-linked accounts

    @GetMapping("/linked-providers")
    public ResponseEntity<List<String>> getLinkedProviders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<String> providers = accountLinkingService.getLinkedProviders(user.getUserId());
        return ResponseEntity.ok(providers);
    }

    @DeleteMapping("/unlink/{provider}")
    public ResponseEntity<LinkAccountResponseDTO> unlinkProvider(
            @PathVariable String provider,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        AuthProvider authProvider = AuthProvider.valueOf(provider.toUpperCase());

        accountLinkingService.unlinkOAuthProvider(user.getUserId(), authProvider);

        return ResponseEntity.ok(new LinkAccountResponseDTO(
                "Successfully unlinked " + provider,
                provider,
                false
        ));
    }
}
