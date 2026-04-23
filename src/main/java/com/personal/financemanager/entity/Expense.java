package com.personal.financemanager.entity;

import java.time.LocalDate;

public record Expense(
     Long id,
     Double amount,
     String category,
     String description,
     LocalDate transcation_date,
     String payment_type
) {}
