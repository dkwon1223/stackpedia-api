package com.dkwondev.stackpedia_v2_api.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkAccountResponseDTO {
    private String message;
    private String provider;
    private boolean linked;
}