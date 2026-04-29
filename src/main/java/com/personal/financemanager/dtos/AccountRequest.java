package com.personal.financemanager.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountRequest {
    private Long id;
    private String accountName;

    @Positive(message = "Balance must be greater than 0")
    private BigDecimal balance;
    private Long userId;
}
