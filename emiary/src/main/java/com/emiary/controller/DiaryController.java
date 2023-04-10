package com.emiary.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;

import com.emiary.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emiary.service.DiaryService;
import com.emiary.service.ImageGenerationService;
import com.emiary.util.EmotionAnalyzer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("diary")
@Controller
public class DiaryController {
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
     * @throws IOException 
     */

    @ResponseBody
    @PostMapping("write")
    public double write(Diaries diaries, @AuthenticationPrincipal UserDetails user, Model model) throws IOException {
        log.debug("diaries.getCreated_at() : {}", diaries.getCreated_at());
    	EmotionAnalysisResult result = EmotionAnalyzer.analyzeEmotion(diaries.getContent());
    	System.out.print("컨트롤러 일기분석 결과 :  " + result);
        diaries.setEmotionscore(result.getScore());
        diaries.setKeyword(result.getNoun());
        
        String wordsForAi = String.join(",", result.getWordsForAi());
        log.debug("문자열로 바뀐 AI 단어 리스트 : {}", wordsForAi);
        
        String artStyles = "Random Painting";
        
//        Random random = new Random();
//
//        int randomIndex = random.nextInt(artStyles.length);
//
//        String selectedStyle = artStyles[randomIndex];
        
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
        
        
        diaries.setEmail(user.getUsername());
        System.out.print("일기쓰기 전의 다이어리 객체 : " + diaries);
        
        //일기 쓰기
        int n = diaryservice.write(diaries);
        
        Diaries diary = diaryservice.read(diaries.getCreated_at(), user.getUsername());
        System.out.print( "쓰기에서 일기 저장 직후 일기객체 : " + diary);
        
        String text = diary.getWordsforai();
        log.debug("\n다이어리에 저장된 키워드! : {}", text);
        int diary_id = diary.getDiary_id();
        System.out.print("다이어리 아이디! : " + diary_id + "\n");
        imageGenerationService.generateImage(text, diary_id);
        
        return result.getScore();
    }
    
//    @ResponseBody
//    @GetMapping(value = "/image/{text}")
//    public String getImage(@PathVariable String text ) throws IOException {
//        log.debug("키워드 이미지 컨트롤러 쪽 들어왔나? : {}", text);
//        String url = imageGenerationService.generateImage(text);
//        log.debug("컨트롤러로 반환된 url : {}", url);
//        if (url == null) {
//            log.debug("이미지 저장실패");
//            
//            return url;
//        }
//       
//        return "redirect:/";

    @GetMapping("read")
    public String read(String dayString, @AuthenticationPrincipal UserDetails user, Model model){
    	log.debug("리드의 데이스트링 : {}" , dayString);
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
