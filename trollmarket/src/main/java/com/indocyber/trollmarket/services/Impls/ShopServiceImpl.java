package com.indocyber.trollmarket.services.Impls;

import com.indocyber.trollmarket.dtos.cart.CartRequestDto;
import com.indocyber.trollmarket.dtos.product.ProductListDto;
import com.indocyber.trollmarket.dtos.product.ProductSearchDto;
import com.indocyber.trollmarket.dtos.shipper.ShipperDropdownDto;
import com.indocyber.trollmarket.models.Cart;
import com.indocyber.trollmarket.repositories.CartRepository;
import com.indocyber.trollmarket.repositories.ProductRepository;
import com.indocyber.trollmarket.repositories.ShipperRepository;
import com.indocyber.trollmarket.repositories.UserRepository;
import com.indocyber.trollmarket.services.ShopService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ShipperRepository shipperRepository;
    private final CartRepository cartRepository;

    public ShopServiceImpl(ProductRepository productRepository, UserRepository userRepository, ShipperRepository shipperRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.shipperRepository = shipperRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Page<ProductListDto> getAllProductBySearch(ProductSearchDto dto, Integer pageNumber, Integer pageSize) {
        String checkedName = dto.getName() == null || dto.getName().isBlank() ? null : dto.getName();
        String checkedCategory = dto.getCategory() == null || dto.getCategory().isBlank() ? null : dto.getCategory();
        String checkedDescription = dto.getDescription() == null || dto.getDescription().isBlank() ? null : dto.getDescription();

        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1, pageSize == null ? 1 : pageSize);

        var productPage = productRepository.getAllBySearch(pageable,checkedName,checkedCategory,checkedDescription);

        List<ProductListDto> products = productPage.stream().map(product -> ProductListDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .discontinue(product.getDiscontinue() ? "Yes" : "No")
                .description(product.getDescription())
                .price(product.getPrice())
                .priceCurrency(product.getPriceCurrency())
                .sellerName(product.getUser().getName())
                .build()).toList();

        return new PageImpl<>(products,pageable,productPage.getTotalElements());
    }

    @Override
    public void addProductToCart(Integer id, String name, CartRequestDto dto) {
        var user = userRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
        var shipper = shipperRepository.findById(dto.getShipperId()).orElseThrow(() -> new EntityNotFoundException("Shipper Not Found"));

        var cartOptional = cartRepository.findByAccountIdAndProductId(product.getId(),name);
        if (product.getId().equals(id) && cartOptional.isPresent()){
            var cart = cartOptional.get();
            if (cart.getShipper().getId().equals(dto.getShipperId())){
                cart.setQuantity(cart.getQuantity() + dto.getQuantity());
                cartRepository.save(cart);
                return;
            }
        }

        var newCart = Cart.builder()
                .user(user)
                .product(product)
                .shipper(shipper)
                .quantity(dto.getQuantity())
                .build();
        cartRepository.save(newCart);
    }

    @Override
    public List<ShipperDropdownDto> getShippersDropdown() {
        var shippers = shipperRepository.findAllByService();
        return shippers.stream().map(shipper -> ShipperDropdownDto.builder()
                .id(shipper.getId())
                .name(shipper.getName())
                .build()).toList();
    }
}
