package com.enigma.spring_fikri_demo.service.impl;

import com.enigma.spring_fikri_demo.dto.request.TransactionDetailRequest;
import com.enigma.spring_fikri_demo.dto.request.TransactionRequest;
import com.enigma.spring_fikri_demo.dto.response.*;
import com.enigma.spring_fikri_demo.entity.Customer;
import com.enigma.spring_fikri_demo.entity.Menu;
import com.enigma.spring_fikri_demo.entity.Transaction;
import com.enigma.spring_fikri_demo.entity.TransactionDetail;
import com.enigma.spring_fikri_demo.repository.CustomerRepository;
import com.enigma.spring_fikri_demo.repository.MenuRepository;
import com.enigma.spring_fikri_demo.repository.TransactionRepository;
import com.enigma.spring_fikri_demo.service.TransactionService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setNotes(request.getNotes());

        List<TransactionDetail> transactionDetail = new ArrayList<>();
        double totalAmount = 0.0;

        for (TransactionDetailRequest detailRequest : request.getTransactionDetails()) {
            Menu menu = menuRepository.findById(detailRequest.getMenuId()).orElseThrow(()
                    -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found"));

            if (menu.getIsAvailable() == null || !menu.getIsAvailable()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu '" + menu.getName() + "' is not available");
            }

            double currentPrice = menu.getPrice();
            int quantity = detailRequest.getQuantity();
            double subtotal = currentPrice * quantity;

            TransactionDetail detail = TransactionDetail.builder()
                    .menu(menu)
                    .quantity(quantity)
                    .price(currentPrice)
                    .subtotal(subtotal)
                    .transaction(transaction)
                    .build();
            transactionDetail.add(detail);
            totalAmount = totalAmount + subtotal;
        }
        transaction.setTransactionDetails(transactionDetail);
        transaction.setTotalAmount(totalAmount);

        Transaction saved = transactionRepository.save(transaction);
        return mapToTransactionResponse(saved);

    }

    // Method Tambahan
    @Transactional(readOnly = true)
    @Override
    public TransactionResponse findById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
        );
        return mapToTransactionResponse(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionSummaryResponse> findAll(String customerId, LocalDate startDate, LocalDate endDate, Pageable pageable) {

        Specification<Transaction> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (customerId != null && !customerId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), customerId));
            }

            if (startDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), startDateTime));
            }

            if (endDate != null) {
                LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), endDateTime));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Transaction> transactionPage = transactionRepository.findAll(spec, pageable);

        return transactionPage.map(this::mapToTransactionSummaryResponse);
    }

    private TransactionResponse mapToTransactionResponse (Transaction transaction) {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(transaction.getCustomer().getId())
                .name(transaction.getCustomer().getName())
                .phone(transaction.getCustomer().getPhone())
                .email(transaction.getCustomer().getEmail())
                .address(transaction.getCustomer().getAddress())
                .CreateAt(transaction.getCustomer().getCreatedAt())
                .UpdateAt(transaction.getCustomer().getUpdatedAt())
                .build();
        List<TransactionDetailResponse> detailResponses = transaction.getTransactionDetails().stream()
                .map(this::mapToTransactionDetailResponse)
                .collect(Collectors.toList());

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customer(customerResponse)
                .transactionDate(transaction.getTransactionDate())
                .notes(transaction.getNotes())
                .transactionDetails(detailResponses)
                .totalAmount(transaction.getTotalAmount())
                .build();
    }

    private TransactionDetailResponse mapToTransactionDetailResponse(TransactionDetail detail) {
        MenuResponse menuResponse = MenuResponse.builder()
                .id(detail.getMenu().getId())
                .name(detail.getMenu().getName())
                .price(detail.getMenu().getPrice())
                .description(detail.getMenu().getDescription())
                .category(detail.getMenu().getCategory())
                .isAvailable(detail.getMenu().getIsAvailable())
                .build();

        return TransactionDetailResponse.builder()
                .id(detail.getId())
                .menu(menuResponse)
                .quantity(detail.getQuantity())
                .price(detail.getPrice())
                .subtotal(detail.getSubtotal())
                .build();
    }

    private TransactionSummaryResponse mapToTransactionSummaryResponse(Transaction transaction) {
        return TransactionSummaryResponse.builder()
                .id(transaction.getId())
                .customerName(transaction.getCustomer() != null ? transaction.getCustomer().getName() : "N/A")
                .transactionDate(transaction.getTransactionDate())
                .totalAmount(transaction.getTotalAmount())
                .build();
    }

}
