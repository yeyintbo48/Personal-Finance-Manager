package com.personal.financemanager;

import java.math.BigDecimal;
import com.personal.financemanager.entity.TransationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransationRequest(
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    BigDecimal amount,

    @NotBlank(message = "Description is required")
    String description,

    @NotNull(message = "Type is required")
    TransationType type
) {}
