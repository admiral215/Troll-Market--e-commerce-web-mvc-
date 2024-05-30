package com.indocyber.trollmarket.services.Impls;

import com.indocyber.trollmarket.dtos.product.ProductUpsertDto;
import com.indocyber.trollmarket.dtos.product.ProductListDto;
import com.indocyber.trollmarket.models.Product;
import com.indocyber.trollmarket.repositories.ProductRepository;
import com.indocyber.trollmarket.repositories.UserRepository;
import com.indocyber.trollmarket.services.MerchandiseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchandiseServiceImpl implements MerchandiseService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public MerchandiseServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<ProductListDto> getProductsByAccountId(String username, Integer pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber == null ? 0 : pageNumber - 1
                ,10);

        var productPage = productRepository.getAllByAccountId(pageable,username);

        List<ProductListDto> products = productPage.stream().map(product -> ProductListDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .discontinue(product.getDiscontinue() ? "Yes" : "No")
                .price(product.getPrice())
                .description(product.getDescription())
                .priceCurrency(product.getPriceCurrency())
                .isHasOrder(!product.getOrders().isEmpty())
                .isHasCart(!product.getCarts().isEmpty())
                .build()).toList();

        return new PageImpl<>(products, pageable, productPage.getTotalElements());
    }

    @Override
    public void insertProduct(ProductUpsertDto dto, String name) {
        var user = userRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("User not found"));

        var product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .discontinue(dto.getDiscontinue() != null)
                .user(user)
                .build();
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductUpsertDto dto, Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setDiscontinue(dto.getDiscontinue() != null);
        productRepository.save(product);
    }

    @Override
    public ProductUpsertDto getProduct(Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return ProductUpsertDto.builder()
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .discontinue(product.getDiscontinue())
                .build();
    }

    @Override
    public void deleteProduct(Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public Boolean isOrdering(Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return !product.getOrders().isEmpty();
    }

    @Override
    public void discontinueProduct(Integer id) {
        var product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setDiscontinue(true);
        productRepository.save(product);
    }
}
