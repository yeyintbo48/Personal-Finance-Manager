package com.personal.financemanager.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountRequest {
    private Long id;
    private String accountname;
    private BigDecimal balance;
    private Long userId;
}
