package com.indocyber.trollmarket.dtos.shipper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ShipperUpsertDto {
    private final Integer id;

    @NotNull
    @NotBlank
    private final String name;

    @NotNull
    @Positive
    private final Double price;

    private final Boolean isService;
}
