package com.personal.financemanager.dtos;

import java.math.BigDecimal;
import com.personal.financemanager.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    BigDecimal amount,

    @NotBlank(message = "Description is required")
    String description,

    @NotNull(message = "Type is required")
    TransactionType type,
    Long userId,
    Long accountId
) {}
