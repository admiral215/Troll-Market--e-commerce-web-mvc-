package com.indocyber.trollmarket.services.Impls;


import com.indocyber.trollmarket.configuration.FormatterIndonesiaCurrency;
import com.indocyber.trollmarket.dtos.cart.CartListDto;
import com.indocyber.trollmarket.dtos.cart.CartPurchaseDto;
import com.indocyber.trollmarket.dtos.cart.CartSizeDto;
import com.indocyber.trollmarket.dtos.shipper.ShipperInfoCartDto;
import com.indocyber.trollmarket.models.Cart;
import com.indocyber.trollmarket.models.Order;
import com.indocyber.trollmarket.models.Shipper;
import com.indocyber.trollmarket.repositories.CartRepository;
import com.indocyber.trollmarket.repositories.OrderRepository;
import com.indocyber.trollmarket.repositories.UserRepository;
import com.indocyber.trollmarket.services.MyCartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
public class MyCartServiceImpl implements MyCartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FormatterIndonesiaCurrency formatter;

    public MyCartServiceImpl(CartRepository cartRepository, UserRepository userRepository, OrderRepository orderRepository, FormatterIndonesiaCurrency formatter) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.formatter = formatter;
    }

    @Override
    public Page<CartListDto> getAllCart(String name, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1, 10);

        var cartPage = cartRepository.findByAccountId(pageable,name);

        List<CartListDto> carts = cartPage.stream().map(cart -> CartListDto.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .seller(cart.getUser().getName())
                .shipment(cart.getShipper().getName())
                .product(cart.getProduct().getName())
                .totalPrice(cart.getTotalPriceCurrency())
                .build()).toList();

        return new PageImpl<>(carts,pageable,cartPage.getTotalElements());
    }

    @Override
    public CartSizeDto getSizeCarts(String name) {
        var user = userRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return CartSizeDto.builder()
                .accountId(user.getAccountId())
                .cartSize(user.getCarts().size())
                .build();
    }

    @Override
    public void purchaseAllCart(String name) {
        var user = userRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var carts = user.getCarts();
        if (carts.isEmpty()){
            throw new IllegalArgumentException("Cart List is Empty");
        }

        BigDecimal totalPrice = countTotalPriceCarts(carts);
        boolean isInsufficientBalance = user.getBalance().compareTo(totalPrice) < 0 ;

        if (isInsufficientBalance){
            throw new IllegalArgumentException("Insufficient Balance");
        }

        addOrderFromCart(carts);

        user.setBalance(user.getBalance().subtract(totalPrice));
        userRepository.save(user);

        cartRepository.deleteByAccountId(user.getAccountId());
    }

    private BigDecimal countTotalPriceCarts(List<Cart> carts) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (var cart : carts) {
            totalPrice = totalPrice.add(cart.getTotalPrice());
        }
        return totalPrice;
    }

    @Override
    public boolean checkBalanceAndCarts(String name) {
        var user = userRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var carts = user.getCarts();
        if (carts.isEmpty()){
            return true;
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (var cart : carts) {
            totalPrice = totalPrice.add(cart.getProduct().getPrice());
        }
        return user.getBalance().compareTo(totalPrice) < 0;
    }

    @Override
    public void deleteCartById(BigInteger id) {
        var cart = cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        cartRepository.deleteById(id);
    }

    @Override
    public CartPurchaseDto getPurchaseInfo(String username) {
        var user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var carts = user.getCarts();

        var totalPrice = countTotalPriceCarts(carts);
        List<ShipperInfoCartDto> shippers = carts.stream().map(cart -> ShipperInfoCartDto.builder()
                .name(cart.getShipper().getName())
                .price(formatter.format(cart.getShipper().getPrice()))
                .build()).toList();
        System.out.println(shippers);
        return CartPurchaseDto.builder()
                .shippers(shippers)
                .totalPrice(formatter.format(totalPrice))
                .build();
    }


    public void addOrderFromCart(List<Cart> carts){
        for (var cart : carts){
            var order = Order.builder()
                    .orderDate(LocalDate.now())
                    .quantity(cart.getQuantity())
                    .unitPrice(cart.getProduct().getPrice())
                    .user(cart.getUser())
                    .shipper(cart.getShipper())
                    .product(cart.getProduct())
                    .build();
            addBalanceSeller(cart);
            orderRepository.save(order);
        }
    }

    private void addBalanceSeller(Cart cart) {
        var seller = cart.getProduct().getUser();
        BigDecimal totalPrice = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
        BigDecimal balance = seller.getBalance().add(totalPrice);
        seller.setBalance(balance);
    }
}
