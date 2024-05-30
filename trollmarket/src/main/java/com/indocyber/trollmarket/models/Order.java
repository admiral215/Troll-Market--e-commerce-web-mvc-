package com.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private BigInteger id;

    @Column(name = "OrderDate", nullable = false)
    private LocalDate orderDate;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ProductPrice", nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ShipperId")
    private Shipper shipper;

    public BigDecimal getTotalPrice(){
        return unitPrice.multiply(BigDecimal.valueOf(quantity)).add(BigDecimal.valueOf(shipper.getPrice()));
    }

    public BigDecimal getTotalPriceWithoutFreight(){
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public String getOrderDateFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(orderDate);
    }
}
