package com.emiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class FriendController {

	@GetMapping("friendchat")
	public String friednchat() {
		return "friendView/friendchat";
	}
	
	@GetMapping("friendfind")
	public String friendfind() {
		return "friendView/friendfind";
	}
}
