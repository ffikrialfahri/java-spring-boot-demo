package com.enigma.spring_fikri_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Double totalAmount;
    private LocalDateTime transactionDate;
    private String notes;
    private CustomerResponse customer;
    private List<TransactionDetailResponse> transactionDetails;
}