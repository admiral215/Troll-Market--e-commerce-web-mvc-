package com.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "AccountId")
    private String accountId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Balance", nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "AccountId")
    private Account account;

    public String getBalanceCurrency(){
        var format = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return format.format(this.balance);
    }
}
