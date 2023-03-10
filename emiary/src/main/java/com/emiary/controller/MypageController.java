package com.emiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("mypage")
	public String mypage() {
		return "mypage";
	}
	

}
