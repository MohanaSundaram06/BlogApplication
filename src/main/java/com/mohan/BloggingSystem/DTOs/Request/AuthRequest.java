package com.mohan.BloggingSystem.DTOs.Request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username ;
    private String password;
}
