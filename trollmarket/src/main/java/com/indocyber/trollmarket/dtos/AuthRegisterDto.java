package com.indocyber.trollmarket.dtos;

import com.indocyber.trollmarket.validator.ComparePasswordAdmin;
import com.indocyber.trollmarket.validator.ComparePasswordUser;
import com.indocyber.trollmarket.validator.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ComparePasswordUser
public class AuthRegisterDto {
    @NotBlank
    @NotNull
    @Username
    private final String username;

    @NotBlank
    @NotNull
    private final String password;

    @NotBlank
    @NotNull
    private final String confirmPassword;

    @NotBlank
    @NotNull
    private final String name;

    @NotBlank
    @NotNull
    private final String address;
}
