package com.enigma.spring_fikri_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionSummaryResponse {
    private String id;
    private String customerName;
    private LocalDateTime transactionDate;
    private Double totalAmount;
}