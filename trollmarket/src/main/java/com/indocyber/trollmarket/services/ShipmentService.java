package com.indocyber.trollmarket.services;

import com.indocyber.trollmarket.dtos.shipper.ShipperListDto;
import com.indocyber.trollmarket.dtos.shipper.ShipperUpsertDto;
import org.springframework.data.domain.Page;

public interface ShipmentService {
    Page<ShipperListDto> getAllShipment(Integer pageNumber);

    void upsertShipper(ShipperUpsertDto dto);

    void offServiceShipper(Integer id);

    boolean isHasOrder(Integer id);

    void deleteShipperById(Integer id);
}
