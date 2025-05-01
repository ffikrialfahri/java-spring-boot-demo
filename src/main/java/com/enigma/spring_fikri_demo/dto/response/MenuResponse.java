package com.enigma.spring_fikri_demo.dto.response;

import com.enigma.spring_fikri_demo.entity.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private MenuCategory category;
    private Boolean isAvailable;
}
