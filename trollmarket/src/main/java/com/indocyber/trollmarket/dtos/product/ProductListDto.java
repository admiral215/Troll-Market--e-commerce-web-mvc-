package com.indocyber.trollmarket.dtos.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductListDto {
    private final Integer id;
    private final String name;
    private final String category;
    private final String discontinue;
    private final String description;
    private final BigDecimal price;
    private final String priceCurrency;
    private final String sellerName;
    private final Boolean isHasOrder;
    private final Boolean isHasCart;
}
