package com.indocyber.trollmarket.services;

import com.indocyber.trollmarket.dtos.order.OrderListDto;
import com.indocyber.trollmarket.dtos.user.UserDepositDto;
import com.indocyber.trollmarket.dtos.user.UserItemDto;
import org.springframework.data.domain.Page;

public interface ProfileService {
    UserItemDto getUser(String username);

    void depositBalance(String username , UserDepositDto dto);

    Page<OrderListDto> getOrders(String username, Integer pageNumber, String role);
}
