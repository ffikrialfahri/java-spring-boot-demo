package com.enigma.spring_fikri_demo.repository;

import com.enigma.spring_fikri_demo.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {

}
