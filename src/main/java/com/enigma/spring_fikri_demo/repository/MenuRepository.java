package com.enigma.spring_fikri_demo.repository;

import com.enigma.spring_fikri_demo.entity.Menu;
import com.enigma.spring_fikri_demo.entity.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, String> {
    Page<Menu> findByCategory(MenuCategory category, Pageable pageable);
}