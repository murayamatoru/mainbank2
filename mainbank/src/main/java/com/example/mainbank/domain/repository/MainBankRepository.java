package com.example.mainbank.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mainbank.domain.model.MainBank;

/**
 * 取引銀行リポジトリ
 */
@Repository
public interface MainBankRepository extends JpaRepository<MainBank, Integer> {
}
