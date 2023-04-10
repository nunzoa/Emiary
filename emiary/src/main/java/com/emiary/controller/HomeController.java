package com.emiary.controller;

import com.emiary.domain.Diaries;
import com.emiary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	DiaryService diaryService;

	@GetMapping("/")
	    public String main(){
	        return "mainView/homePage";
	    }

	@ResponseBody
	@GetMapping("findingContent")
	public List<Diaries> findingContent(String searchInput, @AuthenticationPrincipal UserDetails userDetails){
		List<Diaries> contents = diaryService.findingContent(searchInput, userDetails.getUsername());
		return contents;
	}

}
