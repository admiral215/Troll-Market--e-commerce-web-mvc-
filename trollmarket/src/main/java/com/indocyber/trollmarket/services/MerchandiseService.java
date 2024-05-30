package com.indocyber.trollmarket.services;

import com.indocyber.trollmarket.dtos.product.ProductUpsertDto;
import com.indocyber.trollmarket.dtos.product.ProductListDto;
import org.springframework.data.domain.Page;

public interface MerchandiseService {
    Page<ProductListDto> getProductsByAccountId(String username, Integer pageNumber);

    void insertProduct(ProductUpsertDto dto, String name);

    void updateProduct(ProductUpsertDto dto, Integer id);

    ProductUpsertDto getProduct(Integer id);

    void deleteProduct(Integer id);

    Boolean isOrdering(Integer id);

    void discontinueProduct(Integer id);
}
