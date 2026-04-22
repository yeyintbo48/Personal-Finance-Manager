package com.personal.financemanager.dtos;

public record RegisterRequest(
    String username,
    String email,
    String password
){}
