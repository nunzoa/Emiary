package com.emiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.emiary.domain.Diaries;
import com.emiary.service.DiaryService;
import com.emiary.util.EmotionAnalyzer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("diary")
@Controller
public class DiaryController {
    @Autowired
    DiaryService diaryservice;

    @GetMapping("write")
    public String write(String dayString, Model model) {
        model.addAttribute("dayString", dayString);
        return "diaryView/writeForm";
    }
    /**
     * 다이어리 저장
     * @param diaries 제목과 본문이 담긴 다이어리 객체
     * @param user 유저 로그인 정보가 담긴 객체 파라미터
     * @return 캘린더 홈으로 이동
     */

    @ResponseBody
    @PostMapping("write")
    public int write(String content, String created_at, @AuthenticationPrincipal UserDetails user) {
//        double score = EmotionAnalyzer.analyzeEmotion(diaries.getContent());
//        log.debug(" 감정분석결과 점수 : {}",score);
//
//        diaries.setEmotion_id(score);
//        diaries.setEmail(user.getUsername());
        log.debug("{}", content);
        log.debug("{}", created_at);
        int n = diaryservice.write(null);

        return n;
    }
}
