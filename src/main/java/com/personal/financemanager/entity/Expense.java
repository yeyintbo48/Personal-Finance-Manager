package com.personal.financemanager.entity;

import java.time.LocalDate;

public record Expense(
     Long id,
     double amount,
     String category,
     String description,
     LocalDate transactionDate,
     String paymentType
) {}
