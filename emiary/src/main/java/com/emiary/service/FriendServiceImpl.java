package com.emiary.service;

import com.emiary.dao.FriendDAO;
import com.emiary.domain.FindFriend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService{

    @Autowired
    FriendDAO friendDAO;

    @Override
    public List<FindFriend> getFriendInfo(String username) {
        List<FindFriend> friendList = friendDAO.getFriendInfo(username);
        return friendList;
    }

    @Override
    public String likeFriend(String email, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("friend_email", email);
        map.put("user_email", username);
        int n = friendDAO.likeFriend(map);

        String result = "";
        // 반대 경우도 liked인지 확인
        Optional<String> check = friendDAO.checkMatched(map);

        if (check.isPresent() && check.get().equals("LIKED")) {
            // 둘 다 matched로 업데이트 해준다
            result = "MATCHED";

            int x = friendDAO.insertMatchedUser(map);

            int y = friendDAO.insertMatchedFriend(map);

            log.debug("x : {}, y :  {}", x, y);
        } else {
            result = "NOTHING";
        }

        return result;
    }
}
