package com.indocyber.trollmarket.dtos.cart;

import com.indocyber.trollmarket.dtos.shipper.ShipperInfoCartDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
public class CartPurchaseDto {
    private String totalPrice;
    private List<ShipperInfoCartDto> shippers;
}
