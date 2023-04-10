package com.emiary.friendTest;

import com.emiary.dao.FriendDAO;
import com.emiary.domain.FindFriend;
import com.emiary.service.FriendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.function.Predicate;

@SpringBootTest
public class friend {

    @Autowired
    FriendDAO friendDAO;

    @Test
    void testDelete(){
        String email = "twinyoung96@gmail.com";
        List<FindFriend> create = friendDAO.getFriendInfo(email);
        List<FindFriend> delete = friendDAO.friendMatched(email);

        int count = create.size() - 1;
        for (int i = count; i >= 0; i--) {
            FindFriend ff = create.get(i);
            for (int j = 0; j < delete.size(); j++) {

                FindFriend dd = delete.get(j);
                if (ff.getEmail().equals(dd.getEmail())) {
                    System.out.println("del : " + count);
                    System.out.println(create.remove(count));
                }
            }
            count--;
        }



    }
}
