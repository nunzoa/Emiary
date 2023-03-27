package com.emiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import lombok.extern.slf4j.Slf4j;
import com.emiary.domain.Member;
import com.emiary.service.MemberService;

import java.util.HashMap;
import java.util.Map;

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
	 * 로그인 폼으로 이동
	 * @return
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "memberView/loginForm";
	}

	/**
	 * 회원가입 폼으로 이동
	 * @return
	 */
	@GetMapping("register")
	public String register() {
		return "memberView/registerForm";
	}

	/**
	 * 회원가입 후 로그인 창으로 이동
	 * @param member
	 * @return
	 */
	@ResponseBody
	@PostMapping("register")
	public int register(Member member){
		int n = service.insert(member);
		return n;
	}



}
	


