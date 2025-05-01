package com.enigma.spring_fikri_demo.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime CreateAt;
    private LocalDateTime UpdateAt;
}