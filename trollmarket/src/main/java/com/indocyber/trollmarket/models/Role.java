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
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @ManyToMany(mappedBy = "roles")
    Set<Account> accounts;
}
