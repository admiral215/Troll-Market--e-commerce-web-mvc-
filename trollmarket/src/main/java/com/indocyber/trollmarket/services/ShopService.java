package com.indocyber.trollmarket.services;

import com.indocyber.trollmarket.dtos.cart.CartRequestDto;
import com.indocyber.trollmarket.dtos.product.ProductListDto;
import com.indocyber.trollmarket.dtos.product.ProductSearchDto;
import com.indocyber.trollmarket.dtos.shipper.ShipperDropdownDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopService {
    Page<ProductListDto> getAllProductBySearch(ProductSearchDto dto, Integer pageNumber, Integer pageSize);

    void addProductToCart(Integer id, String name, CartRequestDto dto);

    List<ShipperDropdownDto> getShippersDropdown();
}
