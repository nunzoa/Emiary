package com.emiary.controller;

import com.emiary.domain.Diaries;
import com.emiary.service.GalleryService;
import com.emiary.util.InstagramParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


    @GetMapping("insta")
    public String insta() {
        return "galleryview/instaGallery";
    }

    @ResponseBody
    @GetMapping("getInsta")
    public String getInsta(@AuthenticationPrincipal UserDetails userDetails) throws IOException {

        String keyword = galleryService.getKeyword(userDetails.getUsername());

        log.debug("keyword : {} ", keyword);
        
        InstagramParser instagramParser = new InstagramParser();
        log.debug("이게 문제인가?");
        String value = instagramParser.getValue("동물");
        log.debug("동물결과 : {}", value);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(value);

        return jsonString;
    }

}