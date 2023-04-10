package com.emiary.service;

import com.cloudinary.Cloudinary;
import com.emiary.dao.MyPageDAO;
import com.emiary.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MypageService{

    private final Cloudinary cloudinary;

    @Autowired
    MyPageDAO myPageDAO;

    @Autowired
    PasswordEncoder encoder;


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
    public int modify(Member member) {
        String pw = encoder.encode(member.getMemberpw());
        member.setMemberpw(pw);
        int result = myPageDAO.modify(member);

        return result;
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


    @Override
    public int delete(Member member) {
        int delChild = myPageDAO.deletChildMember(member);
        int result = myPageDAO.deleteMember(member);
        return result;
    }


    @Override
    public int inputURL(String imageURL, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("imageURL", imageURL);

        int result = myPageDAO.inputURL(map);
        return result;
    }

    @Override
    public String getImgURL(String username) {
        String imgURL = myPageDAO.getImgURL(username);
        return imgURL;
    }

    @Override
    public int countFriends(String username) {
        int n = myPageDAO.countFriends(username);
        return n;
    }

}
