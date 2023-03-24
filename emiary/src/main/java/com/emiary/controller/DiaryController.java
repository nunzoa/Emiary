package com.emiary.controller;

import com.emiary.domain.EmotionColor;
import com.emiary.util.EmotionAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.emiary.domain.Diaries;
import com.emiary.service.DiaryService;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("diary")
@Controller
public class DiaryController {
    @Autowired
    DiaryService diaryservice;

    @GetMapping("write")
    public String write(String dayString, Model model) {
        log.debug("dayString {}", dayString);
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
    public int write(Diaries diaries, @AuthenticationPrincipal UserDetails user) {
        double score = EmotionAnalyzer.analyzeEmotion(diaries.getContent());
        log.debug(" 감정분석결과 점수 : {}",score);
        diaries.setEmotionscore(score);
        diaries.setEmail(user.getUsername());
        log.debug("{}", diaries);
        int n = diaryservice.write(diaries);

        return n;
    }

    @GetMapping("read")
    public String read(String dayString, @AuthenticationPrincipal UserDetails user, Model model){
        Diaries diary = diaryservice.read(dayString, user.getUsername());
        String date = diary.getCreated_at();
        date = date.replaceFirst("-", "년 ").replace("-", "월 ").concat("일");
        model.addAttribute("content", diary.getContent());
        model.addAttribute("created_at", date);

        return "diaryView/readForm";
    }

    @ResponseBody
    @GetMapping("checkDiary")
    public List<EmotionColor> checkDiary(@AuthenticationPrincipal UserDetails user){

        List<Diaries> diaries = diaryservice.checkDiary(user.getUsername());
        List<EmotionColor> emotionColors = new ArrayList<>();

        for(Diaries diary : diaries){
            emotionColors.add(new EmotionColor(diary.getCreated_at(), diary.getEmotionscore()));
        }


        log.debug("{}", emotionColors);

        return emotionColors;
    }
    @ResponseBody
    @GetMapping("modalCheck")
    public int modalCheck(@RequestParam("dateForOne") String dateForOne){
        log.debug(dateForOne);
        int n = diaryservice.modalCheck(dateForOne);
        log.debug("콘트롤러 n의 값은? ", n);
        return n;
    }
}
