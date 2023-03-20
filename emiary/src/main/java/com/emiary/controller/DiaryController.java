package com.emiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("list")
    public String list(){
        return "diaryView/list";
    }
    /**
     * @return 다이어리  작성 폼으로 이동
     */
    @GetMapping("write")
    public String write() {
        return "diaryView/writeForm";
    }
    /**
     * 다이어리 저장
     * @param diaries 제목과 본문이 담긴 다이어리 객체
     * @param user 유저 로그인 정보가 담긴 객체 파라미터
     * @return 캘린더 홈으로 이동
     */
    @PostMapping("write")
    public String write(Diaries diaries, @AuthenticationPrincipal UserDetails user) {
        double score = EmotionAnalyzer.analyzeEmotion(diaries.getContent());
        log.debug(" 감정분석결과 점수 : {}",score);

        diaries.setEmotion_id(score);
        diaries.setEmail(user.getUsername());

        diaryservice.write(diaries);
        return "redirect:/";
    }
}
