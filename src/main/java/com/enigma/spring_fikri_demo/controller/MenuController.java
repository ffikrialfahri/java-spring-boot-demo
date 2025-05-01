package com.enigma.spring_fikri_demo.controller;

import com.enigma.spring_fikri_demo.constant.ApiEndpoint;
import com.enigma.spring_fikri_demo.dto.CommonResponse;
import com.enigma.spring_fikri_demo.dto.PaginationResponse;
import com.enigma.spring_fikri_demo.dto.request.MenuRequest;
import com.enigma.spring_fikri_demo.dto.response.MenuResponse;
import com.enigma.spring_fikri_demo.entity.MenuCategory;
import com.enigma.spring_fikri_demo.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.MENU)
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CommonResponse<MenuResponse>> createMenu(@Valid @RequestBody MenuRequest menuRequest) {
        MenuResponse menuResponse = menuService.addMenu(menuRequest);
        CommonResponse<MenuResponse> response = new CommonResponse<>(
                "Menu berhasil dibuat",
                HttpStatus.CREATED.value(),
                menuResponse
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(
            @PathVariable String id, @Valid @RequestBody MenuRequest menuRequest
    ) {
        MenuResponse menuResponse = menuService.updateMenu(id, menuRequest);
        CommonResponse<MenuResponse> response = new CommonResponse<>(
                "Menu berhasil diperbarui",
                HttpStatus.OK.value(),
                menuResponse
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        CommonResponse<String> response = new CommonResponse<>(
                "Menu berhasil dihapus",
                HttpStatus.OK.value(), null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenus (Pageable pageable, @RequestParam(required = false) MenuCategory category) {
        Page<MenuResponse> menupage = menuService.findAllMenus(pageable, category);

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalElements(menupage.getTotalElements())
                .totalPages(menupage.getTotalPages())
                .build();
        CommonResponse<List<MenuResponse>> response = new CommonResponse<>(
                "Daftar menu berhasil diambil",
                HttpStatus.OK.value(),
                menupage.getContent(),
                paginationResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuById(@PathVariable String id) {
        MenuResponse menuResponse = menuService.findById(id);
        CommonResponse<MenuResponse> response = new CommonResponse<>(
                "Menu ditemukan",
                HttpStatus.OK.value(),
                menuResponse
        );
        return ResponseEntity.ok(response);
    }
}

