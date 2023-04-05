package com.emiary.dao;

import com.emiary.domain.FindFriend;
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
}
