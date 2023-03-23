package com.emiary.diaryTest;


import com.emiary.domain.Diaries;
import com.emiary.service.DiaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CheckingDiaryTest {


    @Autowired
    DiaryService diaryService;

    @Test
    public void checkingDiaryTest(){
        List<Diaries> diaries = diaryService.checkDiary("twinyoung96@gmail.com");

        List<String> checkingDiaries = new ArrayList<>();
        for(Diaries diary : diaries){
            checkingDiaries.add(diary.getCreated_at());
        }

        System.out.println(checkingDiaries);
    }
}
