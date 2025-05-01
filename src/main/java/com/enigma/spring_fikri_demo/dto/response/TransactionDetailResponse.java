package com.enigma.spring_fikri_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String id;
    private Integer quantity;
    private Double price;
    private Double subtotal;
    private MenuResponse  menu;
}