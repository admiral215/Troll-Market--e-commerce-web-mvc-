package com.indocyber.trollmarket.dtos.history;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoryBuyerDropdownDto {
    private final String id;
    private final String name;
}
