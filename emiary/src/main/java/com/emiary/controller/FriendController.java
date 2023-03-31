package com.emiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("friend")
public class FriendController {

	@GetMapping("findFriend")
	public String findFriend() {

		return "friendView/findFriend";
	}

}
