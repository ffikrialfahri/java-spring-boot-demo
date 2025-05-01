package com.enigma.spring_fikri_demo.controller;

import com.enigma.spring_fikri_demo.constant.ApiEndpoint;
import com.enigma.spring_fikri_demo.dto.CommonResponse;
import com.enigma.spring_fikri_demo.dto.PaginationResponse;
import com.enigma.spring_fikri_demo.dto.request.CustomerRequest;
import com.enigma.spring_fikri_demo.dto.response.CustomerResponse;
import com.enigma.spring_fikri_demo.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> createCsutomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        CommonResponse<CustomerResponse> response = new CommonResponse<>(
                "Customer berhasil dibuat",
                HttpStatus.CREATED.value(),
                customerResponse
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(
            @PathVariable String id, @Valid @RequestBody CustomerRequest customerRequest
    ) {
        CustomerResponse customerResponse = customerService.updateCustomer(id, customerRequest);
        CommonResponse<CustomerResponse> response = new CommonResponse<>(
                "Customer berhasil diperbarui",
                HttpStatus.OK.value(),
                customerResponse
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        CommonResponse<String> response = new CommonResponse<>(
                "Customer berhasil dihapus",
                HttpStatus.OK.value(), null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomers(Pageable pageable, @RequestParam(required = false) String name) {
        Page<CustomerResponse> customerPage = customerService.findAllCustomers(pageable, name);

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
        CommonResponse<List<CustomerResponse>> response = new CommonResponse<>(
                "Daftar customer berhasil diambil",
                HttpStatus.OK.value(),
                customerPage.getContent(),
                paginationResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> findById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.findById(id);
        CommonResponse<CustomerResponse> response = new CommonResponse<>(
                "Customer ditemukan",
                HttpStatus.OK.value(),
                customerResponse
        );
        return ResponseEntity.ok(response);
    }
}