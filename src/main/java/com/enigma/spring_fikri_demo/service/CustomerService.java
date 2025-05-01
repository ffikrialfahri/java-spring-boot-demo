package com.enigma.spring_fikri_demo.service;

import com.enigma.spring_fikri_demo.dto.request.CustomerRequest;
import com.enigma.spring_fikri_demo.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(String id, CustomerRequest updateRequest);
    void deleteCustomer(String id);
    CustomerResponse findById(String id);
    Page<CustomerResponse> findAllCustomers (Pageable pageable, String name );
}
