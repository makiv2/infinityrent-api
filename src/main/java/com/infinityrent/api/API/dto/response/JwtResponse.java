package com.infinityrent.api.API.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private final String iss = "https://api.infinity-rent.com"; //TODO: set correct iss
    private final String type = "Bearer";
    private String token;
    private Date iat;
    private Date exp;
    private Long sub;
    private String username;
    private String email;
    private List<String> roles;
}
