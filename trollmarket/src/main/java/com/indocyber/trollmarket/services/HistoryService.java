package com.indocyber.trollmarket.services;

import com.indocyber.trollmarket.dtos.history.HistorySearchDto;
import com.indocyber.trollmarket.dtos.history.HistorySellerDropdownDto;
import com.indocyber.trollmarket.dtos.order.OrderHistoryDto;
import com.indocyber.trollmarket.models.RoleEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HistoryService {
    List<HistorySellerDropdownDto> getDropdownUserByRole(RoleEnum roleEnum);

    Page<OrderHistoryDto> getAllBySearch(HistorySearchDto dto, Integer pageNumber);
}
