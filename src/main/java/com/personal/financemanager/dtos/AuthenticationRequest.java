package com.personal.financemanager.dtos;

public record AuthenticationRequest(
    String username,
    String email,
    String password
){}
