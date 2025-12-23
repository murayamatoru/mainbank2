package com.example.mainbank.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.mainbank.application.TestDataService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private TestDataService testDataService;

	@GetMapping("/")
	public ModelAndView showHome(ModelAndView mav) {
		mav.addObject("Title", "取引銀行管理システム");
		mav.setViewName("home");
		return mav;
	}
	
	/**
	 * テスト用データを作成する
	 * @param mav
	 * @return
	 */
	@GetMapping("test/data/create")
	public ModelAndView createTestData(ModelAndView mav) {
		mav.addObject("Title", "取引銀行管理（テストデータ作成結果）");
		
		boolean result = this.testDataService.createTestData();
		mav.setViewName("test/data/created");
		
		return mav;
	}
}
