package com.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "ProductId", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ShipperId")
    private Shipper shipper;

    public String getTotalPriceCurrency(){
        var format = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return format.format(this.product.getPrice().multiply(BigDecimal.valueOf(quantity))
                .add(BigDecimal.valueOf(shipper.getPrice())));
    }

    public BigDecimal getTotalPrice(){
        return product.getPrice().multiply(BigDecimal.valueOf(quantity))
                .add(BigDecimal.valueOf(shipper.getPrice()));
    }
}
