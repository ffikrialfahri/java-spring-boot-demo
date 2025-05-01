package com.enigma.spring_fikri_demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    @NotBlank(message = "Customer ID cannot be empty")
    private String customerId;
    @Size(max = 100, message = "Notes maksimal 100 karakter")
    private String notes;
    @NotEmpty(message = "Transaction details cannot be empty")
    private List<@Valid TransactionDetailRequest> transactionDetails;
}