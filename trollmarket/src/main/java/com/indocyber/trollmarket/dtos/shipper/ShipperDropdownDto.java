package com.indocyber.trollmarket.dtos.shipper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipperDropdownDto {
    private final Integer id;
    private final String name;
}
