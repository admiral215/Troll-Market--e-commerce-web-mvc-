package com.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Category", nullable = false)
    private String category;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Discontinue")
    private Boolean discontinue;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

    public String getPriceCurrency(){
        var format = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return format.format(this.price);
    }

}
