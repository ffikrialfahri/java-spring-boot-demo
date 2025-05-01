package com.enigma.spring_fikri_demo.service;

import com.enigma.spring_fikri_demo.dto.request.MenuRequest;
import com.enigma.spring_fikri_demo.dto.response.MenuResponse;
import com.enigma.spring_fikri_demo.entity.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuService {
    MenuResponse addMenu(MenuRequest request);
    MenuResponse updateMenu(String id, MenuRequest updateRequest);
    void deleteMenu(String id);
    MenuResponse findById(String id);
    Page<MenuResponse> findAllMenus(Pageable pageable, MenuCategory category);
}