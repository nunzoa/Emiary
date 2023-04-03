package com.emiary.service;

import com.emiary.dao.MyPageDAO;
import com.emiary.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MyPageServiceImpl implements MypageService{

    @Autowired
    MyPageDAO myPageDAO;

    @Override
    public int countDiaries(String username) {
        int n = myPageDAO.countDiaries(username);
        return n;
    }

    @Override
    public double calcEmotion(String username) {
        Double n = myPageDAO.calcEmotion(username);
        if(n == null)  n = 0d;
        n = n.doubleValue();

        return n;
    }

    @Override
    public char checkProfile(String username) {
        char isProfile = myPageDAO.checkProfile(username);

        return isProfile;
    }

    @Override
    public int allowpf(String isAllowed, String username) {
        if(isAllowed.equals("Y")){
            isAllowed = "N";
        }else if(isAllowed.equals("N")){
            isAllowed = "Y";
        }

        Map<String, String> map = new HashMap<>();
        map.put("isAllowed", isAllowed);
        map.put("username", username);
        int n = myPageDAO.allowpf(map);
        return 0;
    }

    @Override
    public String changeImg(Member member) {
        int n = myPageDAO.changeImg(member);

        String img = myPageDAO.getImage(member);
        return img;
    }

    @Override
    public Member read(String username) {

        Member member = myPageDAO.selectMember(username);
        return member;
    }

//    @Override
//    public int countFriends(String username) {
//        int n = myPageDAO.countFriends(username);
//        return n;
//    }


}
