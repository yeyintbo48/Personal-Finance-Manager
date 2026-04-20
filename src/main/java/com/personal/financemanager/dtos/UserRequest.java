package com.personal.financemanager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {
    private Long id;

    @NotBlank(message = "Usename is required!")
    private String username;

    @NotBlank(message = "Email is required!")
    private String email;

    @NotNull(message = "Password is required!")
    private String password;
}
