package com.emiary.service;

import com.emiary.domain.FindFriend;

import java.util.List;

public interface FriendService {
    List<FindFriend> getFriendInfo(String username);

    String likeFriend(String email, String username);
}
