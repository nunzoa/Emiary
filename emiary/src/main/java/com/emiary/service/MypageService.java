package com.emiary.service;


import com.emiary.domain.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MypageService {
    int countDiaries(String username);

    double calcEmotion(String username);

    char checkProfile(String username);

    int allowpf(String isAllowed, String username);

    String changeImg(Member member);

    Member read(String username);

    int modify(Member member);

    int delete(Member member);

    int countFriends(String username);

    int inputURL(String imageURL, String username);

    String getImgURL(String username);
}
