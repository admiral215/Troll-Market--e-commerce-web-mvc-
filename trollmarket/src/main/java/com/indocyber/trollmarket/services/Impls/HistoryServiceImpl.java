package com.indocyber.trollmarket.services.Impls;

import com.indocyber.trollmarket.configuration.FormatterIndonesiaCurrency;
import com.indocyber.trollmarket.dtos.history.HistorySearchDto;
import com.indocyber.trollmarket.dtos.history.HistorySellerDropdownDto;
import com.indocyber.trollmarket.dtos.order.OrderHistoryDto;
import com.indocyber.trollmarket.models.RoleEnum;
import com.indocyber.trollmarket.repositories.OrderRepository;
import com.indocyber.trollmarket.repositories.UserRepository;
import com.indocyber.trollmarket.services.HistoryService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final UserRepository userRepository;
    private final FormatterIndonesiaCurrency formatter;
    private final OrderRepository orderRepository;

    public HistoryServiceImpl(UserRepository userRepository, FormatterIndonesiaCurrency formatter, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.formatter = formatter;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<HistorySellerDropdownDto> getDropdownUserByRole(RoleEnum roleEnum) {
        var sellers = userRepository.findByRole(roleEnum);
        return sellers.stream().map(user -> HistorySellerDropdownDto.builder()
                .id(user.getAccountId())
                .name(user.getName())
                .build()).toList();
    }

    @Override
    public Page<OrderHistoryDto> getAllBySearch(HistorySearchDto dto, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1, 10, Sort.by("id").descending());
        var orderPage = orderRepository.getAllBySearch(pageable, dto.getBuyerId(), dto.getSellerId());
        var orders = orderPage.stream().map(order -> OrderHistoryDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDateFormatted())
                .buyer(order.getUser().getName())
                .seller(order.getProduct().getUser().getName())
                .product(order.getProduct().getName())
                .quantity(order.getQuantity())
                .shipment(order.getShipper().getName())
                .totalPrice(formatter.format(order.getTotalPrice()))
                .build()).toList();


        return new PageImpl<>(orders,pageable,orderPage.getTotalElements());
    }

}
