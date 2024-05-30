package com.indocyber.trollmarket.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductUpsertDto {
    @NotNull
    @NotBlank
    private final String name;

    @NotNull
    @NotBlank
    private final String category;

    private final String description;

    @NotNull
    @Positive
    private final BigDecimal price;

    private final Boolean discontinue;
}
