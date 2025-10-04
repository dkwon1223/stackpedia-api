package com.dkwondev.stackpedia_v2_api.controller;

import com.dkwondev.stackpedia_v2_api.model.dto.auth.LoginRequestDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.auth.LoginResponseDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.auth.UserDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.model.mapper.UserMapper;
import com.dkwondev.stackpedia_v2_api.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody User user) {
        User newUser = authenticationService.signup(user);
        return new ResponseEntity<>(userMapper.userToUserDTO(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO body) {
        LoginResponseDTO loginResponse = authenticationService.login(body.getUsername(), body.getPassword());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        authenticationService.verifyEmail(token);
        return new ResponseEntity<>("Email verified successfully. You can now log in.", HttpStatus.OK);
    }
}
