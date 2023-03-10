package com.emiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import com.emiary.domain.Member;
import com.emiary.service.MemberService;

/**
 * 회원 관련 처리 콘트롤러
 */
@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {

	@Autowired
	MemberService service;
	
	/**
	 * 회원 가입 폼으로 이동
	 * @return 회원가입 양식 HTML
	 */
	@GetMapping("join")
	public String join() {
		
		return "memberView/joinForm";
	}
	
	@PostMapping("join")
	public String join(Member member) {
		log.debug("가입데이터 : {}", member);
		service.insert(member);
		return "redirect:/";
	}
	
	@GetMapping("emailcheck")
	public String emailcheck() {
		return "memberView/emailcheck";
	}
	
	@PostMapping("emailcheck")
	public String emailcheck(String email, Model model) {
		log.debug("검색할 아이디 : {}", email);
		
		boolean res = service.emailcheck(email);
		
		model.addAttribute("searchEmail", email);
		model.addAttribute("result", res);
		log.debug("검색할 아이디 : {}", email);
		log.debug("검색할 아이디 : {}", res);
		return "memberView/emailcheck";
	}
	
	/**
	 * 로그인 폼으로 이동
	 * @return
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "memberView/loginForm";
	}
	
	

	
	

}
