package com.enigma.spring_fikri_demo.service;

import com.enigma.spring_fikri_demo.dto.request.TransactionRequest;
import com.enigma.spring_fikri_demo.dto.response.TransactionResponse;
import com.enigma.spring_fikri_demo.dto.response.TransactionSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    TransactionResponse findById(String id);
    Page<TransactionSummaryResponse> findAll(String customerId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}