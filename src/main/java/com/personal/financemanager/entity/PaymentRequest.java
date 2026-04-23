package com.personal.financemanager.entity;

public record PaymentRequest(
    Double amount,
    String description,
    String category,
    String password
){}
