package com.indocyber.trollmarket.dtos.shipper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipperInfoCartDto {
    private String name;
    private String price;
}
