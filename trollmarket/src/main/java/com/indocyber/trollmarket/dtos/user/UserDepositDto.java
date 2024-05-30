package com.indocyber.trollmarket.dtos.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserDepositDto {
    @NotNull
    @Positive
    private BigDecimal balance;
}
