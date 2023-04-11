package com.emiary.controller;

import com.emiary.domain.Diaries;
import com.emiary.service.GalleryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("gallery")
@Controller
@Slf4j
public class GalleryController {

    @Autowired
    GalleryService galleryService;

    @GetMapping("/home")
    public String home() {
        return "galleryview/arts";
    }

    @ResponseBody
    @GetMapping("getAIImg")
    public List<Diaries> getAIImg(String keyword, String yearAndMonth, @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("년월 {}", yearAndMonth);
        List<Diaries> diaries = galleryService.getAIImg(keyword, yearAndMonth, userDetails.getUsername());

        return diaries;
    }
}