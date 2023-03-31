package com.emiary.diaryTest;


import com.emiary.dao.DiaryDAO;
import com.emiary.domain.Diaries;
import com.emiary.service.DiaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.ServletOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class CheckingDiaryTest {


    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryDAO diaryDAO;

    @Test
    public void checkingDiaryTest(){
        List<Diaries> diaries = diaryService.checkDiary("twinyoung96@gmail.com");

        List<String> checkingDiaries = new ArrayList<>();
        for(Diaries diary : diaries){
            checkingDiaries.add(diary.getCreated_at());
        }

        System.out.println(checkingDiaries);
    }

    @Test
    public void testDiary(){

        String givenDate = "2023-03-01";
        String diaryNotExisted = "";
        int count = 1;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;

        try {
            date = sdf.parse(givenDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        검사하고 null이면 빠져나가고
//        null이 아니면 무한 반복
        String dateCheck = sdf.format(date);



    }

    @Test
    public void testCalendar() throws ParseException {


        String givenDate = "2023-3-17";


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int resultDate = 1;
        String sendDate = "";
        int count = 0;

            while(resultDate != 0){
                Date date = sdf.parse(givenDate);
                cal.setTime(date);
                count++;
                cal.add(Calendar.DATE, -count);
                Date time = cal.getTime();
                sendDate = sdf.format(time);
                Map<String, String> map = new HashMap<>();
                map.put("dayString", sendDate);
                map.put("username", "twinyoung96@gmail.com");

                resultDate = diaryDAO.emptyDiaryCheck(map);
            }

        System.out.println(
                "이거 실행되냐?"+ sendDate
        );
    }



    @Test
    public void testDiaryCheck(){

        String dayString =  "2023-3-17";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Diaries isDiaryEmpty = null;
        String sendDate = "";
        int count = 0;

        while(isDiaryEmpty == null){
            Date date = null;
            try {
                date = sdf.parse(dayString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            cal.setTime(date);
            count++;
            cal.add(Calendar.DATE, count);
            Date time = cal.getTime();
            sendDate = sdf.format(time);
            Map<String, String> map = new HashMap<>();
            map.put("dayString", sendDate);
            map.put("username", "twinyoung96@gmail.com");

            isDiaryEmpty = diaryDAO.writtenDiaryCheck(map);
        }

        System.out.println(isDiaryEmpty);

    }
}
