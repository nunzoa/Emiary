package com.emiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("main")
public class MainController {

	@GetMapping("list")
    public String list(){
        return "list";
    }
	
	@GetMapping("main")
    public String main(){
        return "main";
    }
	
	@GetMapping("mypage")
	public String mypage() {
		return "mypage";
	}
	
	@GetMapping("update")
	public String update() {
		return "/update";
	}
	
	@GetMapping("friendchat")
	public String friednchat() {
		return "/friendchat";
	}
	
	@GetMapping("friendfind")
	public String friendfind() {
		return "/friendfind";
	}
}
