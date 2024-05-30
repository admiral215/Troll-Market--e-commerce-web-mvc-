package com.indocyber.trollmarket.dtos;

import com.indocyber.trollmarket.models.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleRequestDto {
    @NotBlank
    @NotNull
    private final String role;
}
