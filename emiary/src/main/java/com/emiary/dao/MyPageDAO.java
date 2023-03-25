package com.emiary.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageDAO {
    int countDiaries(String username);
    Double calcEmotion(String username);
//    int countFriends(String username);
}
