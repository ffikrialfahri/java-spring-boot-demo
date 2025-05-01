package com.enigma.spring_fikri_demo.controller;

import com.enigma.spring_fikri_demo.constant.ApiEndpoint;
import com.enigma.spring_fikri_demo.dto.CommonResponse;
import com.enigma.spring_fikri_demo.dto.PaginationResponse;
import com.enigma.spring_fikri_demo.dto.request.TransactionRequest;
import com.enigma.spring_fikri_demo.dto.response.TransactionResponse;
import com.enigma.spring_fikri_demo.dto.response.TransactionSummaryResponse;
import com.enigma.spring_fikri_demo.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoint.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.create(transactionRequest);
        CommonResponse<TransactionResponse> response  = new CommonResponse<>(
                "Transaksi berhasil dibuat",
                HttpStatus.CREATED.value(),
                transactionResponse
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.findById(id);
        CommonResponse<TransactionResponse> response  = new CommonResponse<>(
                "Transaksi ditemukan",
                HttpStatus.OK.value(),
                transactionResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionSummaryResponse>>> getAllTransactions(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(page = 0, size = 10, sort = "transactionDate") Pageable pageable
    ) {
        Page<TransactionSummaryResponse> transactionPage = transactionService.findAll(customerId, startDate, endDate, pageable);

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(transactionPage.getNumber())
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .pageSize(transactionPage.getSize())
                .build();

        CommonResponse<List<TransactionSummaryResponse>> response = new CommonResponse<>(
                "Daftar transaksi berhasil diambil",
                HttpStatus.OK.value(),
                transactionPage.getContent(),
                paginationResponse
        );
        return ResponseEntity.ok(response);
    }
}