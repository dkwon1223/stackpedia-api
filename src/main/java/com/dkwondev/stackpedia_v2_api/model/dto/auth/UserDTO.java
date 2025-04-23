package com.dkwondev.stackpedia_v2_api.model.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
}
