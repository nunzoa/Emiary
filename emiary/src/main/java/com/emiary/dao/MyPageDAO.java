package com.emiary.dao;

import com.emiary.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MyPageDAO {
    int countDiaries(String username);
    Double calcEmotion(String username);

    char checkProfile(String username);

    int allowpf(Map<String, String> map);

    int changeImg(Member member);

    String getImage(Member member);

    Member selectMember(String username);
//    int countFriends(String username);
}
