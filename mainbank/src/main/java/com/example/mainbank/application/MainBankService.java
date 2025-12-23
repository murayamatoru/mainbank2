package com.example.mainbank.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mainbank.domain.model.MainBank;
import com.example.mainbank.domain.repository.MainBankRepository;

/**
 * 取引銀行サービス
 */
@Service
public class MainBankService {

//	@PersistenceContext
//	private EntityManager em;
	
	@Autowired
	private MainBankRepository mainBankRepository;
	
	/**
	 * 1件の取引先銀行を保存する
	 * @param mainBank
	 * @return
	 */
	public boolean addMainMank(MainBank mainBank) {
		mainBankRepository.save(mainBank);
		return true;
	}
	
}
