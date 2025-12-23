package com.example.mainbank.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import com.example.mainbank.domain.model.enums.BankCategory;

/**
 * 銀行
 */
@Entity
public class Bank extends BaseEntity {
	
	/** 銀行名 */
	@Column(length = 1000)
	private String name = "";
	
	/** 銀行分類 */
	@Enumerated(EnumType.STRING)
	private BankCategory  category;
	
	@OneToMany(mappedBy="bank", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<MainBank> mainBankList;
	
	/** デフォルトコンストラクタ */
	public Bank() {
		super();
	}
	
	/** コンストラクタ */
	public Bank(String name, BankCategory  category) {
		super();
		this.name = name;
		this.category = category;
		this.mainBankList = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BankCategory getCategory() {
		return category;
	}

	public void setCategory(BankCategory category) {
		this.category = category;
	}
	
	public void addMainBank(MainBank mainBank) {
		this.mainBankList.add(mainBank);
		mainBank.setBank(this);
	}

	public List<MainBank> getMainBankList() {
		return mainBankList;
	}

	public void setMainBankList(List<MainBank> mainBankList) {
		this.mainBankList = mainBankList;
	}
}

/*
列挙型のマッピングについて
@Enumeratedで指定できる値は，次のどちらかの値です。
(1) EnumType.ORDINAL：数値型 ordinal()値で登録される
(2) EnumType.STRING：文字列型 name()値で登録される
もし(1)(2)以外を使いたい場合（例：都市銀行ではなく都銀）は
@Enumeratedではなく@Convertとして
AttributeConverterを実装し、
テーブルスキーマを変更（ALTER TABLE ...）
する必要がある。
Hibernateによるスキーマ自動更新だけでは更新できなくなる。（ALTER TABLE ...）
また(1) は列挙型の名前の定義順で変動するがあるので既存データとの不整合がおきるリスクがある。
(2) が最も良い選択と思われる。
 */