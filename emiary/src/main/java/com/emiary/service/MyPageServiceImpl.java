package com.emiary.service;

import com.emiary.dao.MyPageDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        log.debug("n의 값은? {}", n);
        if(n == null)  n = 0d;
        n = n.doubleValue();

        return n;
    }

//    @Override
//    public int countFriends(String username) {
//        int n = myPageDAO.countFriends(username);
//        return n;
//    }
}
