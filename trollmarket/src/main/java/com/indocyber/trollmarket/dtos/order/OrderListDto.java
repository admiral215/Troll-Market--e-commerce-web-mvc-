package com.indocyber.trollmarket.dtos.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class OrderListDto {
    private final BigInteger id;
    private final String orderDate;
    private final String product;
    private final Integer quantity;
    private final String shipment;
    private final String totalPrice;
}
