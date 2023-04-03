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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

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
	public String changeImg(Member member, MultipartFile upload, @AuthenticationPrincipal UserDetails userDetails, Model model){
		log.debug("여기 오니???");
		if ( upload != null && !upload.isEmpty()) {
			String filename = FileService.saveFile(upload, uploadPath);
			member.setOriginalfile(upload.getOriginalFilename());
			member.setSavedfile(filename);
		}
		member.setEmail(userDetails.getUsername());

		log.debug("member : {}", member);

		String img = mypageService.changeImg(member);
		model.addAttribute("pImg", img);
		log.debug("img : {}", img);
		return "redirect:home";
	}


	@GetMapping("download")
	public String download(@AuthenticationPrincipal UserDetails user
			, HttpServletResponse response) {
		Member member = mypageService.read(user.getUsername());
		if (member == null || member.getSavedfile() == null) {
			return "redirect:list";
		}
		String file = uploadPath + "/" + member.getSavedfile();
		FileInputStream in = null;
		ServletOutputStream out = null;
		try {
			//응답 정보의 헤더 세팅
			response.setHeader("Content-Disposition",
					" attachment;filename="+ URLEncoder.encode(member.getOriginalfile(), "UTF-8"));

			in = new FileInputStream(file);
			out = response.getOutputStream();
			//파일 전송
			FileCopyUtils.copy(in, out);

			in.close();
			out.close();
		}
		catch (IOException e) {	//예외 메시지 출력
		}
		return "redirect:home";
	}
}
