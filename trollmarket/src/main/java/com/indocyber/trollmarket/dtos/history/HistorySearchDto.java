package com.indocyber.trollmarket.dtos.history;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistorySearchDto {
    private  String buyerId;
    private  String sellerId;
}
