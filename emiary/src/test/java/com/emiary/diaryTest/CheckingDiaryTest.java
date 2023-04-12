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


    @Test
    public void 빈출단어Test(){
        String diary = "오늘은 아침에 일어나자마자 머릿속에 떠오른 것이 팬케이크였다. 어젯밤에 텔레비전에서 맛있어 보이는 팬케이크 광고를 보고 배가 고팠던 것 같다. 그래서 나는 어제 늦게 자서 아침을 빠뜨렸다. 그래서 오늘 아침은 팬케이크를 먹기로 결심했다.\n" +
                "\n" +
                "냄비를 꺼내서 밀가루, 달걀, 우유, 설탕, 소금을 준비했다. 팬에 식용유를 두르고 불을 켰다. 팬케이크 반죽을 한 숟가락씩 붓기 시작했다. 노릇노릇한 팬케이크가 구워질 때마다, 달콤한 향기가 가득하게 퍼져나갔다.\n" +
                "\n" +
                "팬케이크가 구워지는 동안, 나는 사랑하는 가족들과 함께 먹는 아침 식사를 상상했다. 플레이트에 팬케이크를 올리고, 멜로우와 딸기, 블루베리를 올려서 아름다운 팬케이크 탑을 만들었다. 그리고 달콤한 메이플 시럽을 뿌려서 완성했다. 팬케이크가 고소하고 달콤한 맛으로 내 입 속으로 들어오면서, 머릿속에서 팬케이크 단어가 떠오르기 시작했다.\n" +
                "\n" +
                "아침 식사가 끝나고 난 뒤, 나는 팬케이크를 만들면서 느낀 행복한 순간을 돌아보았다. 팬케이크는 맛뿐만 아니라 나에게 따뜻한 가족의 사랑과 행복한 추억을 떠올리게 해준다. 그래서 나는 앞으로도 자주 팬케이크를 만들어 가족과 함께 특별한 시간을 보내고 싶다는 생각이 들었다";

//
//        System.out.println("nouns : " + nouns);
//        System.out.print("형태소 리스트 길이 : " + nouns.size());
//        Map<String, Integer> counts = new HashMap<>();
//
//        // 각 문자열의 카운트 계산
//        for (String s : nouns) {
//            if (counts.containsKey(s)) {
//                counts.put(s, counts.get(s) + 1);
//            } else {
//                counts.put(s, 1);
//            }
//        }
//
//        int maxCount = 0;
//        List<String> maxCountStrings = new ArrayList<>();
//
//        // 가장 큰 카운트값 찾기
//        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
//            int count = entry.getValue();
//            if (count > maxCount) {
//                maxCount = count;
//                maxCountStrings.clear();
//                maxCountStrings.add(entry.getKey());
//            } else if (count == maxCount) {
//                maxCountStrings.add(entry.getKey());
//            }
//        }
//
//         maxCountStrings.get(0);
    }
}
