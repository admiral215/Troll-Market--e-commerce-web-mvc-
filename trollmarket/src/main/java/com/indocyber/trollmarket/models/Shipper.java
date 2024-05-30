package com.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Shippers")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name" , nullable = false)
    private String name;

    @Column(name = "Price", nullable = false)
    private Double price;

    @Column(name = "IsService", nullable = false)
    private Boolean isService;

    @OneToMany(mappedBy = "shipper")
    private List<Order> orders;

    @OneToMany(mappedBy = "shipper")
    private List<Cart> carts;
}
