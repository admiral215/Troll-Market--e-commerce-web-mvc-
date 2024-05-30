package com.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "AccountRole",
            joinColumns = @JoinColumn(name = "AccountId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    Set<Role> roles;

    @OneToOne(mappedBy = "account")
    @PrimaryKeyJoinColumn
    private User user;
}
