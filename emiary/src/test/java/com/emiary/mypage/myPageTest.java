package com.emiary.mypage;

import com.emiary.dao.MyPageDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class myPageTest {

    @Autowired
    MyPageDAO myPageDAO;

    @Test
    public void testCheckProfile(){
        char c = myPageDAO.checkProfile("twinyoung96@gmail.com");
        System.out.println("char로 나오나? " +  c);
    }
}
