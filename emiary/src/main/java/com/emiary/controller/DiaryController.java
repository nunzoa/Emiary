package com.emiary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emiary.domain.Diaries;
import com.emiary.domain.EmotionAnalysisResult;
import com.emiary.domain.EmotionColor;
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
    public double write(Diaries diaries, @AuthenticationPrincipal UserDetails user, Model model) {
        log.debug("diaries.getCreated_at() : {}", diaries.getCreated_at());
    	EmotionAnalysisResult result = EmotionAnalyzer.analyzeEmotion(diaries.getContent());
        diaries.setEmotionscore(result.getScore());
        diaries.setKeyword(result.getNoun());
        diaries.setEmail(user.getUsername());

        int n = diaryservice.write(diaries);
        return result.getScore();
    }

    @GetMapping("read")
    public String read(String dayString, @AuthenticationPrincipal UserDetails user, Model model){
        Diaries diary = diaryservice.read(dayString, user.getUsername());
        String date = diary.getCreated_at();
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
        return emotionColors;
    }
    @ResponseBody
    @GetMapping("modalCheck")
    public int modalCheck(@RequestParam("dateForOne") String dateForOne, @AuthenticationPrincipal UserDetails userDetails){
        int n = diaryservice.modalCheck(dateForOne, userDetails.getUsername());
        return n;
    }

    @GetMapping("deleteDiary")
    public String deleteDiary(String dayString, @AuthenticationPrincipal UserDetails user) {
        int n = diaryservice.deleteDiary(dayString, user.getUsername());

        return "redirect:/";
    }


    @ResponseBody
    @GetMapping("lastDiary")
    public String lastDiary(String dayString, @AuthenticationPrincipal UserDetails user){

        String dayStringBefore = diaryservice.lastDiary(dayString, user.getUsername());

        return dayStringBefore;
    }

    @ResponseBody
    @GetMapping("nextDiary")
    public String nextDiary(String dayString, @AuthenticationPrincipal UserDetails user){
        String dayStringAfter = diaryservice.nextDiary(dayString, user.getUsername());

        return dayStringAfter;
    }

    @ResponseBody
    @GetMapping("lastReadDiary")
    public Diaries lastReadDiary(String dayString, @AuthenticationPrincipal UserDetails user){
        log.debug("dayString read {} ", dayString);
        Diaries diaries = diaryservice.lastReadDiary(dayString, user.getUsername());

        return diaries;
    }

    @ResponseBody
    @GetMapping("nextReadDiary")
    public Diaries nextReadDiary(String dayString, @AuthenticationPrincipal UserDetails user){
        Diaries diaries = diaryservice.nextReadDiary(dayString, user.getUsername());

        return diaries;
    }


}
