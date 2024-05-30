package com.indocyber.trollmarket.services.Impls;

import com.indocyber.trollmarket.configuration.FormatterIndonesiaCurrency;
import com.indocyber.trollmarket.dtos.order.OrderListDto;
import com.indocyber.trollmarket.dtos.user.UserDepositDto;
import com.indocyber.trollmarket.dtos.user.UserItemDto;
import com.indocyber.trollmarket.models.Account;
import com.indocyber.trollmarket.models.Role;
import com.indocyber.trollmarket.models.RoleEnum;
import com.indocyber.trollmarket.repositories.OrderRepository;
import com.indocyber.trollmarket.repositories.UserRepository;
import com.indocyber.trollmarket.services.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FormatterIndonesiaCurrency formatter;

    public ProfileServiceImpl(UserRepository userRepository, OrderRepository orderRepository, FormatterIndonesiaCurrency formatter) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.formatter = formatter;
    }

    @Override
    public UserItemDto getUser(String username) {
        var user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserItemDto.builder()
                .id(user.getAccountId())
                .name(user.getName())
                .address(user.getAddress())
                .balance(formatter.format(user.getBalance()))
                .build();
    }

    @Override
    public void depositBalance(String username, UserDepositDto dto) {
        var user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setBalance(user.getBalance().add(dto.getBalance()));
        userRepository.save(user);
    }

    @Override
    public Page<OrderListDto> getOrders(String username, Integer pageNumber, String role) {
        var user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,10, Sort.by("id").descending());



        if (role.equals(RoleEnum.BUYER.name())){
            var orderPage = orderRepository.getAllByBuyerId(pageable,username);
            List<OrderListDto> orders = orderPage.stream().map(order -> OrderListDto.builder()
                    .id(order.getId())
                    .orderDate(order.getOrderDateFormatted())
                    .quantity(order.getQuantity())
                    .product(order.getProduct().getName())
                    .shipment(order.getShipper().getName())
                    .totalPrice("- " + formatter.format(order.getTotalPrice()))
                    .build()).toList();
            return new PageImpl<>(orders,pageable, orderPage.getTotalElements());
        }
        var orderPage = orderRepository.getAllBySellerId(pageable,username);
        List<OrderListDto> orders = orderPage.stream().map(order -> OrderListDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDateFormatted())
                .quantity(order.getQuantity())
                .product(order.getProduct().getName())
                .shipment(order.getShipper().getName())
                .totalPrice("+ " +formatter.format(order.getTotalPriceWithoutFreight()))
                .build()).toList();
        return new PageImpl<>(orders,pageable, orderPage.getTotalElements());
    }

    public boolean hasRole(Account account, RoleEnum roleToCheck) {
        for (Role role : account.getRoles()) {
            if (role.getName().equals(roleToCheck)) {
                return true;
            }
        }
        return false;
    }
}
