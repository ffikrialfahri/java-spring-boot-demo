package com.enigma.spring_fikri_demo.dto.request;

import com.enigma.spring_fikri_demo.entity.MenuCategory;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuRequest {
    @NotBlank(message = "Nama menu tidak boleh kosong")
    @Size(max = 100, message = "Nama menu maksimal 100 karakter")
    private String name;
    @Size(max = 500, message = "Deskripsi maksimal 500 karakter")
    private String description;
    @NotNull(message = "Harga tidak boleh kosong")
    @Min(value = 1000, message = "Harga minimal Rp 1.000")
    private Double price;

    @NotNull(message = "Kategori tidak boleh kosong")
    private MenuCategory category;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAvailable = true;
}
