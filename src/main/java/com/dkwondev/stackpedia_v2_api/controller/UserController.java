package com.dkwondev.stackpedia_v2_api.controller;

import com.dkwondev.stackpedia_v2_api.model.dto.UserDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.User;
import com.dkwondev.stackpedia_v2_api.model.mapper.UserMapper;
import com.dkwondev.stackpedia_v2_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<String> basic() {
        return new ResponseEntity<>("User based access", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody User user) {
        User newUser = userService.signup(user);
        return new ResponseEntity<>(userMapper.userToUserDTO(newUser), HttpStatus.CREATED);
    }
}
