package com.emiary.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emiary.domain.*;
import com.emiary.service.FileUpload;
import com.emiary.service.ImageGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emiary.service.DiaryService;
import com.emiary.util.EmotionAnalyzer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("diary")
@RequiredArgsConstructor
@Controller
public class DiaryController {

    private final FileUpload fileUpload;

    @Autowired
    DiaryService diaryservice;

    @Autowired
    ImageGenerationService imageGenerationService;

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
    public double write(Diaries diaries, @AuthenticationPrincipal UserDetails user, Model model) throws IOException {

    	EmotionAnalysisResult result = EmotionAnalyzer.analyzeEmotion(diaries.getContent());
        log.debug("result : {}", result);
        diaries.setEmotionscore(result.getScore());
        diaries.setKeyword(result.getNoun());
        diaries.setEmail(user.getUsername());

        String wordsForAi = String.join(",", result.getWordsForAi());
        log.debug("문자열로 바뀐 AI 단어 리스트 : {}", wordsForAi);

        String artStyles = "Random Painting";

        double value = result.getScore();
        if (value > 0.1) {
            if (value <= -0.5) {
                wordsForAi += ",negative";
            } else {
                wordsForAi +=",positive";
            }
        }

        String wordsForAi1 = artStyles+ "," + wordsForAi;
        log.debug("최종 ai words : {}", wordsForAi1);
        diaries.setWordsforai(wordsForAi1);

        int n = diaryservice.write(diaries);

        Diaries diary = diaryservice.read(diaries.getCreated_at(), user.getUsername());
        System.out.print( "쓰기에서 일기 저장 직후 일기객체 : " + diary);

        String text = diary.getWordsforai();
        log.debug("다이어리에 저장된 키워드! : {}", text);
        int diary_id = diary.getDiary_id();
        MultipartFile multipartFile = imageGenerationService.generateImage(text, diary_id);
        String aiIMG = fileUpload.uploadFile(multipartFile);
        diary.setAiIMG(aiIMG);
        int updateAIaddr = diaryservice.updateAIAddr(diary);
        log.debug("diaries.getDiary_id() : {}, diaries.getAiIMG() : {}", diary.getDiary_id(), diaries.getAiIMG());
        log.debug("imageURL {}", aiIMG);

        return result.getScore();
    }

    @GetMapping("read")
    public String read(String dayString, @AuthenticationPrincipal UserDetails user, Model model){
        Diaries diary = diaryservice.read(dayString, user.getUsername());
        String date = diary.getCreated_at();
        model.addAttribute("diary", diary);
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

    @ResponseBody
    @GetMapping("heartStatus")
    public int heartStatus(String email, String diaryId){
        int cntHeart = diaryservice.heartStatus(email, diaryId);
        return cntHeart;
    }

    @ResponseBody
    @PostMapping("insertReply")
    public int comment(String comment, String diaryId, @AuthenticationPrincipal UserDetails userDetails){
        int result = diaryservice.inputComment(comment, diaryId, userDetails.getUsername());
        return result;
    }

    @ResponseBody
    @GetMapping("getReply")
    public List<Reply> reply(String diaryId){
        List<Reply> replies = diaryservice.getReply(diaryId);
        return replies;
    }

    @ResponseBody
    @GetMapping("replyAlarm")
    public List<ReplyAlarm> replyAlarm(String yearMonth, @AuthenticationPrincipal UserDetails userDetails){
        log.debug("년월이 떠야해 ! {}", yearMonth );
        List<ReplyAlarm> list = diaryservice.getReplyAlarm(yearMonth, userDetails.getUsername());
        log.debug("맵으로 보내는게 조금 잘못된듯? : {}", list);
        return list;
    }
}
