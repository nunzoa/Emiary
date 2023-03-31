package com.emiary.controller;

import com.emiary.domain.Member;
import com.emiary.service.MypageService;
import com.emiary.util.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("mypage")
public class MypageController {

	@Autowired
	MypageService mypageService;

	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

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


	@ResponseBody
	@GetMapping("checkProfile")
	public char checkProfile(@AuthenticationPrincipal UserDetails userDetails) {

		char isProfile = mypageService.checkProfile(userDetails.getUsername());

		return isProfile;
	}

	@ResponseBody
	@GetMapping("allowpf")
	public char allowpf(String isAllowed, @AuthenticationPrincipal UserDetails userDetails) {

		/* 보내고 */
		int n = mypageService.allowpf(isAllowed, userDetails.getUsername());

		/* 받고 Y, N*/
		char isProfile = mypageService.checkProfile(userDetails.getUsername());


		return isProfile;
	}

	@PostMapping("changeImg")
	public String changeImg(Member member, @RequestParam("inputImage") MultipartFile upload, @AuthenticationPrincipal UserDetails userDetails){

		if ( upload != null && !upload.isEmpty()) {
			String filename = FileService.saveFile(upload, uploadPath);
			member.setOriginalfile(upload.getOriginalFilename());
			member.setSavedfile(filename);
		}
		member.setEmail(userDetails.getUsername());

		log.debug("member : {}", member);

		String img = mypageService.changeImg(member);
		log.debug("img : {}", img);
		return "redirect:home";
	}
}
