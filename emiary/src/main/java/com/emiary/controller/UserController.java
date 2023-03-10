package com.emiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.emiary.domain.User;
import com.emiary.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 회원 관련 처리 콘트롤러
 */
@Slf4j
@RequestMapping("user")
@Controller
public class UserController {

	@Autowired
	UserService service;
	
	/**
	 * 회원 가입 폼으로 이동
	 * @return 회원가입 양식 HTML
	 */
	@GetMapping("join")
	public String join() {
		
		return "userView/joinForm";
	}
	
	@PostMapping("join")
	public String join(User user) {
		log.debug("가입데이터 : {}", user);
		service.insert(user);
		return "redirect:/";
	}
	
	@GetMapping("emailcheck")
	public String emailcheck() {
		return "userView/emailcheck";
	}
	
	@PostMapping("emailcheck")
	public String emailcheck(String email, Model model) {
		log.debug("검색할 아이디 : {}", email);
		
		boolean res = service.emailcheck(email);
		
		model.addAttribute("searchEmail", email);
		model.addAttribute("result", res);
		log.debug("검색할 아이디 : {}", email);
		log.debug("검색할 아이디 : {}", res);
		return "userView/emailcheck";
	}
	
	/**
	 * 로그인 폼으로 이동
	 * @return
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "userView/loginForm";
	}


	
	

}
