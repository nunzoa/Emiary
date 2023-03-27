package com.emiary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emiary.domain.Member;
import com.emiary.service.MemberService;

import lombok.extern.slf4j.Slf4j;

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



