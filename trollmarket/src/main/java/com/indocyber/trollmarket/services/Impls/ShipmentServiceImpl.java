package com.indocyber.trollmarket.services.Impls;

import com.indocyber.trollmarket.configuration.FormatterIndonesiaCurrency;
import com.indocyber.trollmarket.dtos.shipper.ShipperListDto;
import com.indocyber.trollmarket.dtos.shipper.ShipperUpsertDto;
import com.indocyber.trollmarket.models.Shipper;
import com.indocyber.trollmarket.repositories.ShipperRepository;
import com.indocyber.trollmarket.services.ShipmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipperRepository shipperRepository;
    private final FormatterIndonesiaCurrency formatter;

    public ShipmentServiceImpl(ShipperRepository shipperRepository, FormatterIndonesiaCurrency formatter) {
        this.shipperRepository = shipperRepository;
        this.formatter = formatter;
    }

    @Override
    public Page<ShipperListDto> getAllShipment(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1, 10);

        var shipperPage = shipperRepository.findAll(pageable);
        List<ShipperListDto> shippers = shipperPage.stream().map(shipper -> ShipperListDto.builder()
                .id(shipper.getId())
                .name(shipper.getName())
                .priceCurrency(formatter.format(shipper.getPrice()))
                .price(shipper.getPrice())
                .isService(shipper.getIsService())
                .build()).toList();

        return new PageImpl<>(shippers,pageable,shipperPage.getTotalElements());
    }

    @Override
    public void upsertShipper(ShipperUpsertDto dto) {
        if (dto.getId() != null){
            var shipper = shipperRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Shipper not found"));
            shipper.setName(dto.getName());
            shipper.setPrice(dto.getPrice());
            shipper.setIsService(dto.getIsService());
            shipperRepository.save(shipper);
            return;
        }
        var shipper = Shipper.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .isService(dto.getIsService() != null)
                .build();
        shipperRepository.save(shipper);
    }

    @Override
    public void offServiceShipper(Integer id) {
        var shipper = shipperRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shipper not found"));
        shipper.setIsService(false);
        shipperRepository.save(shipper);
    }

    @Override
    public boolean isHasOrder(Integer id) {
        var shipper = shipperRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shipper not found"));
        return !shipper.getOrders().isEmpty();
    }

    @Override
    public void deleteShipperById(Integer id) {
        var shipper = shipperRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shipper not found"));
        shipperRepository.delete(shipper);
    }
}
