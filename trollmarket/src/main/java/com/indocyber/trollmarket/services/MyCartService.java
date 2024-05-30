package com.indocyber.trollmarket.services;

import com.indocyber.trollmarket.dtos.cart.CartListDto;
import com.indocyber.trollmarket.dtos.cart.CartPurchaseDto;
import com.indocyber.trollmarket.dtos.cart.CartSizeDto;
import org.springframework.data.domain.Page;

import java.math.BigInteger;

public interface MyCartService {
    Page<CartListDto> getAllCart(String name, Integer pageNumber);

    CartSizeDto getSizeCarts(String name);

    void purchaseAllCart(String name);

    boolean checkBalanceAndCarts(String name);

    void deleteCartById(BigInteger id);

    CartPurchaseDto getPurchaseInfo(String username);
}
