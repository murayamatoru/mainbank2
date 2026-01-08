package com.example.mainbank.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mainbank.domain.model.Bank;

/**
 * 金融機関リポジトリ
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
}

