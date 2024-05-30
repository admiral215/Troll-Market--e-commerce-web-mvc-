package com.indocyber.trollmarket.dtos.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartRequestDto {
    @NotNull
    @Positive
    private final Integer shipperId;

    @NotNull
    @Positive
    private final Integer quantity;
}
