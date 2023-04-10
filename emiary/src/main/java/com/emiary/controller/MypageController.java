package com.emiary.controller;

import com.emiary.domain.Member;
import com.emiary.service.FileUpload;
import com.emiary.service.MypageService;
import com.emiary.util.FileService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("mypage")
public class MypageController {

	private final FileUpload fileUpload;

	@Autowired
	MypageService mypageService;

	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	@GetMapping("home")
	public String mypage(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		int countDiaries = mypageService.countDiaries(userDetails.getUsername());
		double calcEmotion = mypageService.calcEmotion(userDetails.getUsername());
		int countFriends = mypageService.countFriends(userDetails.getUsername());

		model.addAttribute("countDiaries", countDiaries);
		model.addAttribute("calcEmotion", calcEmotion);
		model.addAttribute("countFriends", countFriends);

		return "mypageView/mypage";
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

	@GetMapping("quit")
	public String quit(
			@AuthenticationPrincipal UserDetails userDetails
			, Model model ) {

		return "mypageView/myPageQuit";
	}

	@ResponseBody
	@GetMapping("deleteMember")
	public void deleteMember(Member member, @AuthenticationPrincipal UserDetails user) {

		member.setEmail(user.getUsername());
		mypageService.delete(member);

	}


	@GetMapping("modify")
	public String modify(@AuthenticationPrincipal UserDetails user, Model model) {
		Member member = mypageService.read(user.getUsername());
		log.debug("수정: {}", member);
		model.addAttribute("member", member);
		return "mypageView/myPageModify";
	}

	@ResponseBody
	@PostMapping("modify")
	public String modify(Member member) {
		mypageService.modify(member);
		return "redirect:/mypage/home";
	}

	@PostMapping("upload")
	public String uploadFile(@AuthenticationPrincipal UserDetails userDetails ,@RequestParam("image") MultipartFile multipartFile,
							 Model model) throws IOException {
		String imageURL = fileUpload.uploadFile(multipartFile);
		int result = mypageService.inputURL(imageURL, userDetails.getUsername());
		model.addAttribute("imageURL",imageURL);
		return "redirect:home";
	}


	@ResponseBody
	@GetMapping("getImgURL")
	public String getImgURL(@AuthenticationPrincipal UserDetails userDetails ){
		String imgURL = mypageService.getImgURL(userDetails.getUsername());
		return imgURL;
	}
}
