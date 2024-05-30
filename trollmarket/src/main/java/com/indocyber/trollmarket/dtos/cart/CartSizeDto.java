package com.indocyber.trollmarket.dtos.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartSizeDto {
    private final String accountId;
    private final Integer cartSize;
}
