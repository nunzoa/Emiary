package com.emiary.dao;

import com.emiary.domain.Diaries;
import com.emiary.domain.FindFriend;
import com.emiary.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface FriendDAO {
    List<FindFriend> getFriendInfo(String username);

    int likeFriend(Map<String, String> map);

    Optional<String> checkMatched(Map<String, String> map);

    int insertMatchedUser(Map<String, String> map);

    int insertMatchedFriend(Map<String, String> map);

    List<FindFriend> friendList(String username);

    int checkTodayDiary(String username);

    List<String> todayDiaryVisited(String username);

    List<FindFriend> friendMatched(String username);

    List<FindFriend> getOneFriendList(String username);

    List<Diaries> checkDiary(String nickname);

    Diaries readDiary(Map<String, Object> map);

    int modalCheck(Map<String, String> map);

    Diaries writtenDiaryCheck(Map<String, String> map);

    FindFriend readByNickName(String nickname);

    int visted(Map<String, Object> check);

    int checkDuplicate(Map<String, Object> map);

    Diaries isHeartEmpty(Map<String, Object> map);

    int countHeart(Map<String, Object> map);

    int updateHeart(Map<String, Object> map);

    int inputComment(Map<String, Object> map);

    List<Reply> getReply(String diaryId);

    String getNickName(String writerEmail);
}
