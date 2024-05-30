package com.indocyber.trollmarket.dtos.admin;

import com.indocyber.trollmarket.validator.ComparePasswordAdmin;
import com.indocyber.trollmarket.validator.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ComparePasswordAdmin
public class AdminRegisterDto {
    @NotNull
    @NotBlank
    @Username
    private final String username;

    @NotNull
    @NotBlank
    private final String password;

    @NotNull
    @NotBlank
    private final String confirmPassword;
}
