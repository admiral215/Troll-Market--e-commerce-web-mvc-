package com.indocyber.trollmarket.dtos.shipper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipperListDto {
    private final Integer id;
    private final String name;
    private final String priceCurrency  ;
    private final Double price;
    private final Boolean isService;
}
