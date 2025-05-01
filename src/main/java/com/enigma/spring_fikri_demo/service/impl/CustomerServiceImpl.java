package com.enigma.spring_fikri_demo.service.impl;

import com.enigma.spring_fikri_demo.dto.request.CustomerRequest;
import com.enigma.spring_fikri_demo.dto.response.CustomerResponse;
import com.enigma.spring_fikri_demo.entity.Customer;
import com.enigma.spring_fikri_demo.repository.CustomerRepository;
import com.enigma.spring_fikri_demo.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse addCustomer(CustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phone(request.getPhone())
                .createdAt(request.getCreateAt())
                .updatedAt(request.getUpdateAt())
                .build();

        Customer newCustomer = customerRepository.save(customer);
        return mapToCustomer(newCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse updateCustomer(String id, CustomerRequest updateRequest) {
        Customer findCustomer = findCustomerId(id);

        if (updateRequest.getName() != null) findCustomer.setName(updateRequest.getName());
        if (updateRequest.getEmail() != null) findCustomer.setEmail(updateRequest.getEmail());
        if (updateRequest.getPhone() != null) findCustomer.setPhone(updateRequest.getPhone());
        if (updateRequest.getAddress() != null) findCustomer.setAddress(updateRequest.getAddress());

        Customer updatedCustomer = customerRepository.save(findCustomer);
        return mapToCustomer(updatedCustomer);
    }

    @Transactional (rollbackFor = Exception.class)
    @Override
    public void deleteCustomer(String id) {
        Customer deleteCustomer = findCustomerId(id);
        customerRepository.delete(deleteCustomer);
    }

    @Transactional (readOnly = true)
    @Override
    public CustomerResponse findById(String id) {
        Customer customer = findCustomerId(id);
        return mapToCustomer(customer);
    }

    @Transactional (readOnly = true)
    @Override
    public Page<CustomerResponse> findAllCustomers(Pageable pageable, String name) {
        Page<Customer> customerPage;
        if (name != null) {
            customerPage = customerRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            customerPage = customerRepository.findAll(pageable);
        }
        return customerPage.map(this::mapToCustomer);
    }


    // Method Tambahan
    private Customer findCustomerId(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "Customer with id '" + id + "' not found."));
    }

    private CustomerResponse mapToCustomer(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .CreateAt(customer.getCreatedAt())
                .UpdateAt(customer.getUpdatedAt())
                .build();
    }

}
