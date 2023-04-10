package com.emiary.service;

import com.emiary.dao.FriendDAO;
import com.emiary.domain.Diaries;
import com.emiary.domain.FindFriend;
import com.emiary.domain.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService{

    @Autowired
    FriendDAO friendDAO;

    @Override
    public List<FindFriend> getFriendInfo(String username) {
        List<FindFriend> friendList = friendDAO.getFriendInfo(username);
        List<FindFriend> deleteMatched = friendDAO.friendMatched(username);

        int count = friendList.size() - 1;
        for (int i = count; i >= 0; i--) {
            FindFriend ff = friendList.get(i);
            for (int j = 0; j < deleteMatched.size(); j++) {

                FindFriend dd = deleteMatched.get(j);
                if (ff.getEmail().equals(dd.getEmail())) {
                    System.out.println("del : " + count);
                    System.out.println(friendList.remove(count));
                }
            }
            count--;
        }

        return friendList;
    }

    @Override
    public String likeFriend(String email, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("friend_email", email);
        map.put("user_email", username);
        int n = friendDAO.likeFriend(map);

        String result = "";
        // 반대 경우도 liked인지 확인
        Optional<String> check = friendDAO.checkMatched(map);

        if (check.isPresent() && check.get().equals("LIKED")) {
            // 둘 다 matched로 업데이트 해준다
            result = "MATCHED";

            int x = friendDAO.insertMatchedUser(map);

            int y = friendDAO.insertMatchedFriend(map);

            log.debug("x : {}, y :  {}", x, y);
        } else {
            result = "NOTHING";
        }

        return result;
    }

    @Override
    public List<FindFriend> friendList(String username) {
        List<FindFriend> findFriend = friendDAO.friendList(username);
        log.debug("findFriend : {}", findFriend);


        List<String> emails = friendDAO.todayDiaryVisited(username);

//        0 : 아무것도 아님 1 : 친구가 일단 일기를 씀 -1 : 그 일기를 읽음
        for(FindFriend friendCheck : findFriend){
            int count = friendDAO.checkTodayDiary(friendCheck.getEmail());
            friendCheck.setTodayDiary(0);
            if(count == 1){
                friendCheck.setTodayDiary(1);
            }
            for(String email : emails){
                if(email.equals(friendCheck.getEmail())){
                    friendCheck.setTodayDiary(-1);
                }
            }

        }

        return findFriend;
    }

    @Override
    public List<FindFriend> getOneFriendList(String username) {
        List<FindFriend> getOne  =friendDAO.getOneFriendList(username);
        log.debug("getOne {}", getOne);
        return getOne;
    }


    @Override
    public List<Diaries> checkDiary(String nickname) {
        return friendDAO.checkDiary(nickname);
    }

    @Override
    public Diaries read(String dayString, String nickname, String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("created_at", dayString);
        map.put("nickname", nickname);
        map.put("email", username);
        Diaries diary = friendDAO.readDiary(map);
        map.put("diaryId", diary.getDiary_id());


        log.debug("diary 내용보기 {}", diary);
        System.out.println("username : " + username);

        int count = friendDAO.checkDuplicate(map);
        log.debug("몇개나오는거야 {}", count);


        if (count != 1) {
            Map<String, Object> check = new HashMap<>();
            check.put("username", username);
            check.put("diary_id", diary.getDiary_id());
            int result = friendDAO.visted(check);
            log.debug("이거 작동안한다");
        }


        map.put("username", diary.getEmail());
        log.debug("username : {}, email : {} created_at : {}, diaryId : {}", diary.getEmail(), username, dayString, diary.getDiary_id());

        Diaries heartEmpty = friendDAO.isHeartEmpty(map);
        int cntHeart = friendDAO.countHeart(map);

        log.debug("heartEmpty : {}", heartEmpty);
        log.debug("cntHeart : {}", cntHeart);
        diary.setHeart(heartEmpty.getHeart());
        diary.setCntHeart(cntHeart);

        return diary;
    }

    @Override
    public int modalCheck(String dateForOne, String nickname) {
        Map<String, String> map = new HashMap<>();
        map.put("created_at", dateForOne);
        map.put("nickname", nickname);

        int n = friendDAO.modalCheck(map);

        return n;
    }

    @Override
    public Diaries lastReadDiary(String dayString, String nickname) {
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
            cal.add(Calendar.DATE, -count);
            Date time = cal.getTime();
            sendDate = sdf.format(time);
            Map<String, String> map = new HashMap<>();
            map.put("dayString", sendDate);
            map.put("nickname", nickname);

            isDiaryEmpty = friendDAO.writtenDiaryCheck(map);
        }

        return isDiaryEmpty;
    }

    @Override
    public Diaries nextReadDiary(String dayString, String nickname) {
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
            map.put("nickname", nickname);

            isDiaryEmpty = friendDAO.writtenDiaryCheck(map);
        }

        return isDiaryEmpty;
    }

    @Override
    public FindFriend readByNickName(String nickname) {
        FindFriend oneFriend = friendDAO.readByNickName(nickname);
        log.debug("oneFriend : {}", oneFriend);
        return oneFriend;
    }

    @Override
    public Map<String, Object> isHeartEmpty(String username, String diaryId, String dayString, String email) {



        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("diaryId", diaryId);
        map.put("created_at", dayString);
        map.put("email", email);

        Diaries ch = friendDAO.isHeartEmpty(map);

        if(ch == null){
            ch.setHeart("N");
            ch.setVisited_email(username);
        }
        map.put("heart", ch.getHeart());

        int cntHeart = friendDAO.countHeart(map);

        log.debug("이거 하트랑 개수 체크 {} {} ", ch.getHeart(), cntHeart );

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("cntHeart", cntHeart);
        returnMap.put("checkHeart", ch.getHeart());
        returnMap.put("emailCheck", ch.getVisited_email());
        return returnMap;
    }

    @Override
    public int updateHeart(String diaryId, String heartTo, String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("diaryId", diaryId);
        map.put("username", username);
        map.put("heartTo", heartTo);
        int updateHeart = friendDAO.updateHeart(map);

        int cntHeart = friendDAO.countHeart(map);
        log.debug("업데이트 후 하트 갯수 {}", cntHeart);
        return cntHeart;
    }

    @Override
    public int inputComment(String comment, int diaryId, String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("comment", comment);
        map.put("diaryId", diaryId);
        map.put("username", username);
        log.debug("map 알아보기 {}", map);
        int result = friendDAO.inputComment(map);
        return result;
    }


    @Override
    public List<Reply> getReply(String diaryId) {
        List<Reply> replies = friendDAO.getReply(diaryId);

        for(Reply reply : replies){
            reply.setNickname(friendDAO.getNickName(reply.getWriter_email()));
        }

        return replies;
    }
}
