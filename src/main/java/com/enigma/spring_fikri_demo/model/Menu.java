package com.enigma.spring_fikri_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private String id;
    private String nama;
    private String deskripsi;
    private Double harga;
    private String kategori;
    private Boolean tersedia;
}