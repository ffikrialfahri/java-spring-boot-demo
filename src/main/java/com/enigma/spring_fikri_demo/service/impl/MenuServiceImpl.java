package com.enigma.spring_fikri_demo.service.impl;

import com.enigma.spring_fikri_demo.dto.request.MenuRequest;
import com.enigma.spring_fikri_demo.dto.response.MenuResponse;
import com.enigma.spring_fikri_demo.entity.Menu;
import com.enigma.spring_fikri_demo.entity.MenuCategory;
import com.enigma.spring_fikri_demo.repository.MenuRepository;
import com.enigma.spring_fikri_demo.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse addMenu(MenuRequest request) {
        Menu menu = Menu.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .isAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true)
                .build();

        Menu newmenu = menuRepository.save(menu);
        return mapToMenu(newmenu);
    }

    @Transactional (rollbackFor = Exception.class)
    @Override
    public MenuResponse updateMenu(String id, MenuRequest updateRequest) {
        Menu findMenu = findMenuById(id);

        if (updateRequest.getName() != null) findMenu.setName(updateRequest.getName());
        if (updateRequest.getDescription() != null) findMenu.setDescription(updateRequest.getDescription());
        if (updateRequest.getPrice() != null) findMenu.setPrice(updateRequest.getPrice());
        if (updateRequest.getIsAvailable() != null) findMenu.setIsAvailable(updateRequest.getIsAvailable());

        Menu updatedMenu = menuRepository.save(findMenu);
        return mapToMenu (updatedMenu);
    }

    @Transactional (rollbackFor = Exception.class)
    @Override
    public void deleteMenu(String id) {
        Menu menuToDelete = findMenuById(id);
        menuRepository.delete(menuToDelete);
    }

    @Transactional (readOnly = true)
    @Override
    public MenuResponse findById(String id) {
        Menu menu = findMenuById(id);
        return mapToMenu (menu);
    }

    @Transactional (readOnly = true)
    @Override
    public Page<MenuResponse> findAllMenus(Pageable pageable, MenuCategory category) {
        Page<Menu> menuPage;
        if (category != null) {
            menuPage = menuRepository.findByCategory(category, pageable);
        } else {
            menuPage = menuRepository.findAll(pageable);
        }
        return menuPage.map(this::mapToMenu);
    }


    // Method Tambahan
    private Menu findMenuById(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "Menu with id '" + id + "' not found."));
    }

    private MenuResponse mapToMenu(Menu menu) {
        if (menu == null) {
            return null;
        }
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .category(menu.getCategory())
                .isAvailable(menu.getIsAvailable())
                .build();
    }
}