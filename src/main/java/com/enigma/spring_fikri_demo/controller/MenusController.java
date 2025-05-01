package com.enigma.spring_fikri_demo.controller;

import com.enigma.spring_fikri_demo.model.Menu;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
public class MenusController {
    private List<Menu> menuList = new ArrayList<>();

    // Inisialisasi beberapa menu awal
    public void MenuController() {
        // Menambahkan beberapa menu untuk testing
        Menu nasiGoreng = new Menu(
                UUID.randomUUID().toString(),
                "Nasi Goreng Spesial",
                "Nasi goreng dengan telur, ayam, dan sayuran",
                25000.0,
                "Makanan Utama",
                true
        );

        Menu mieGoreng = new Menu(
                UUID.randomUUID().toString(),
                "Mie Goreng",
                "Mie goreng dengan bakso, telur, dan sayuran",
                22000.0,
                "Makanan Utama",
                true
        );

        Menu esJeruk = new Menu(
                UUID.randomUUID().toString(),
                "Es Jeruk",
                "Minuman segar dari jeruk pilihan",
                8000.0,
                "Minuman",
                true
        );

        menuList.add(nasiGoreng);
        menuList.add(mieGoreng);
        menuList.add(esJeruk);
    }

    // GET - Mendapatkan semua menu
    @GetMapping
    public List<Menu> getAllMenu() {
        return menuList;
    }

    // GET - Mendapatkan menu berdasarkan ID
    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable String id) {
        return menuList.stream()
                .filter(menu -> menu.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // GET - Mencari menu berdasarkan nama
    @GetMapping("/search")
    public List<Menu> searchMenuByName(@RequestParam String nama) {
        return menuList.stream()
                .filter(menu -> menu.getNama().toLowerCase().contains(nama.toLowerCase()))
                .collect(Collectors.toList());
    }

    // GET - Filter menu berdasarkan kategori
    @GetMapping("/kategori/{kategori}")
    public List<Menu> getMenuByCategory(@PathVariable String kategori) {
        return menuList.stream()
                .filter(menu -> menu.getKategori().equalsIgnoreCase(kategori))
                .collect(Collectors.toList());
    }

    // POST - Membuat menu baru
    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        menu.setId(UUID.randomUUID().toString());
        menuList.add(menu);
        return menu;
    }

    // PUT - Memperbarui menu yang sudah ada
    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable String id, @RequestBody Menu updatedMenu) {
        for (int i = 0; i < menuList.size(); i++) {
            Menu menu = menuList.get(i);
            if (menu.getId().equals(id)) {
                updatedMenu.setId(id);
                menuList.set(i, updatedMenu);
                return updatedMenu;
            }
        }
        return null;
    }

    // DELETE - Menghapus menu
    @DeleteMapping("/{id}")
    public boolean deleteMenu(@PathVariable String id) {
        return menuList.removeIf(menu -> menu.getId().equals(id));
    }
}

