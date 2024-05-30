package com.indocyber.trollmarket.dtos.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserItemDto {
    private final String id;
    private final String name;
    private final String role;
    private final String address;
    private final String balance;
}
