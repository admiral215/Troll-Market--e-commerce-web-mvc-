package com.indocyber.trollmarket.dtos.cart;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Builder
@Data
public class CartListDto {
    private final Integer id;
    private final String product;
    private final Integer quantity;
    private final String shipment;
    private final String seller;
    private final String totalPrice;
}
