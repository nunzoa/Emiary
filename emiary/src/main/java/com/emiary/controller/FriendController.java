package com.emiary.controller;

import com.emiary.domain.FindFriend;
import com.emiary.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("friend")
public class FriendController {

	@Autowired
	FriendService friendService;


	@GetMapping("findFriend")
	public String findFriend() {

		return "friendView/findFriend";
	}

	@ResponseBody
	@GetMapping("getFriendInfo")
	public List<FindFriend> getFriendInfo(@AuthenticationPrincipal UserDetails userDetails){
//자기가 아닌 정보만을 불러와야 함
		List<FindFriend> friendList = friendService.getFriendInfo(userDetails.getUsername());
		log.debug("friendList : {}", friendList);
		return friendList;
	}


	@ResponseBody
	@GetMapping("like")
	public String getLike(String email, @AuthenticationPrincipal UserDetails userDetails){
		String check = friendService.likeFriend(email, userDetails.getUsername());
		return check;
	}


//	친구리스트 불러오는 메소드


}
