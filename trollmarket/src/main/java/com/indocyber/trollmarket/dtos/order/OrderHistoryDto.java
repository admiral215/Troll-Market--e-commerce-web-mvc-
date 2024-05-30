package com.indocyber.trollmarket.dtos.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class OrderHistoryDto {
    private final BigInteger id;
    private final String orderDate;
    private final String buyer;
    private final String seller;
    private final String product;
    private final Integer quantity;
    private final String shipment;
    private final String totalPrice;
}
