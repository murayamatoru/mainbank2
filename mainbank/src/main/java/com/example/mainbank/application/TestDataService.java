package com.example.mainbank.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mainbank.domain.model.Bank;
import com.example.mainbank.domain.model.MainBank;
import com.example.mainbank.domain.model.enums.BankCategory;
import com.example.mainbank.domain.repository.BankRepository;
import com.example.mainbank.domain.repository.MainBankRepository;
/**
 * テストデータサービス
 */
@Service
public class TestDataService {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private MainBankRepository mainBankRepository;
	
	/** コンストラクタ */
	public TestDataService() {
		super();
	}
	
	@Transactional
	public boolean createTestData() {
		Bank bk1 =  new Bank("三菱UFJ銀行", BankCategory.都市銀行);
		this.bankRepository.save(bk1);
		
		MainBank mb1 = new MainBank(bk1);
		mb1.setStartDate(LocalDate.now());
		bk1.addMainBank(mb1);
		this.mainBankRepository.save(mb1);
		
		
		List<Bank> bankList = this.bankRepository.findAll();
		
		List<MainBank> mainBankList = this.mainBankRepository.findAll();
		
		int sss = bankList.size() + mainBankList.size();
		
		var sss2 = BankCategory.MAP;
		String sss3 = sss2.toString();
		
		return true;
	}
}
