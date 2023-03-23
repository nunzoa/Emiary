package com.emiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mypage")
public class MypageController {
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("home")
	public String mypage() {
		return "mypageView/mypage";
	}
	

}
