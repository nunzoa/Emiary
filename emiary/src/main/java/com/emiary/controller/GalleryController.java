package com.emiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("gallery")
@Controller
public class GalleryController {

	@GetMapping("/home")
	public String home() {
		return "galleryview/arts";
		
	}
}
