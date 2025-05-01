package com.enigma.spring_fikri_demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String id;

    @NotBlank(message = "Nama menu tidak boleh kosong")
    @Size(max = 100, message = "Nama menu maksimal 100 karakter")
    private String name;
    @Email(message = "Email harus sesuai dengan format")
    private String email;
    @NotBlank(message = "Phone Customer tidak boleh kosong")
    @Min(value = 11, message = "Nomor tidak boleh di bawah 11")
    private String phone;
    @NotBlank(message = "address Customer tidak boleh kosong")
    @Size(max = 200, message = "Alamat maximal 200 kata")
    private String address;
    private LocalDateTime CreateAt;
    private LocalDateTime UpdateAt;
}
