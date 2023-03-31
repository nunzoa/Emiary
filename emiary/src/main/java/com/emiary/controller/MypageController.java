package com.emiary.controller;

import com.emiary.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mypage")
public class MypageController {

	@Autowired
	MypageService mypageService;

	/**
	 * 
	 * @return
	 */
	@GetMapping("home")
	public String mypage(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		int countDiaries = mypageService.countDiaries(userDetails.getUsername());
		double calcEmotion = mypageService.calcEmotion(userDetails.getUsername());
//		int countFriends = mypageService.countFriends(userDetails.getUsername());

		model.addAttribute("countDiaries", countDiaries);
		model.addAttribute("calcEmotion", calcEmotion);
//		model.addAttribute("countFriends", countFriends);

		return "mypageView/mypage";
	}

	@GetMapping("quit")
	public String quit() {


		return "mypageView/myPageQuit";
	}

	@GetMapping("modify")
	public String modify() {


		return "mypageView/myPageModify";
	}
	

}
