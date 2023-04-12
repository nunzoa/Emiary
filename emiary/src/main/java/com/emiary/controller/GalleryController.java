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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("gallery")
@Slf4j
public class GalleryController {

    @Autowired
    GalleryService galleryService;

    @GetMapping("home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("galleryview/arts");
        return modelAndView;
    }

    @GetMapping("getAIImg")
    public List<Diaries> getAIImg(String keyword, String yearAndMonth, @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("년월 {}", yearAndMonth);
        List<Diaries> diaries = galleryService.getAIImg(keyword, yearAndMonth, userDetails.getUsername());

        return diaries;
    }


    @GetMapping("insta")
    public ModelAndView insta() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("galleryview/instaGallery");
        return modelAndView;
    }

    @CrossOrigin(origins = "*") // 허용할 오리진 설정
    @GetMapping("/getInsta")
    public String getInsta(@AuthenticationPrincipal UserDetails userDetails) throws IOException {

        String keyword = galleryService.getKeyword(userDetails.getUsername());

        log.debug("keyword : {} ", keyword);

        InstagramParser instagramParser = new InstagramParser();
        log.debug("이게 문제인가?");
        String value = instagramParser.getValue(keyword);
        log.debug("동물결과 : {}", value);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(value);

        return jsonString;
    }

}