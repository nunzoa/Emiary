package com.emiary.service;

import com.emiary.domain.Diaries;
import com.emiary.domain.FindFriend;
import com.emiary.domain.Reply;

import java.util.List;
import java.util.Map;

public interface FriendService {
    List<FindFriend> getFriendInfo(String username);

    String likeFriend(String email, String username);

    List<FindFriend> friendList(String username);

    List<FindFriend> getOneFriendList(String username);

    Diaries read(String dayString, String nickname, String username);

    List<Diaries> checkDiary(String nickname);

    int modalCheck(String dateForOne, String nickname);

    Diaries lastReadDiary(String dayString, String nickname);

    Diaries nextReadDiary(String dayString, String nickname);

    FindFriend readByNickName(String nickname);

    Map<String, Object> isHeartEmpty(String username, String diaryId, String dayString, String email);

    int updateHeart(String diaryId, String heartTo, String username);

    int inputComment(String comment, int diaryId, String username);

    List<Reply> getReply(String diaryId);
}
