package com.emiary.service;


import com.emiary.domain.Member;

public interface MypageService {
    int countDiaries(String username);

    double calcEmotion(String username);

    char checkProfile(String username);

    int allowpf(String isAllowed, String username);

    String changeImg(Member member);

//    int countFriends(String username);
}
