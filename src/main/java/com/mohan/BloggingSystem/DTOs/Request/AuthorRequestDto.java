package com.mohan.BloggingSystem.DTOs.Request;

import lombok.Data;

@Data
public class AuthorRequestDto {

    private String username;
    private String email;
    private String password;
}
