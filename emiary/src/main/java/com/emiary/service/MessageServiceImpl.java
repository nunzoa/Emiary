package com.emiary.service;

import com.emiary.dao.MessageDAO;
import com.emiary.domain.Member;
import com.emiary.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {


    @Autowired
    MessageDAO messageDAO;

    @Override
    public List<Message> getMessageList(String friendEmail, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("friendEmail", friendEmail);
        map.put("username", username);
        List<Message> messageList = messageDAO.getMessageList(map);

        return messageList;
    }

    @Override
    public int sendMessage(String content, String receiverEmail, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("receiverEmail", receiverEmail);
        map.put("username", username);
        log.debug("map뭐 뜨는지 : {}", map);
        int result = messageDAO.sendMessage(map);

        return result;
    }

    @Override
    public Member getProfile(String friendEmail) {

        Member member = messageDAO.getProfile(friendEmail);

        return member;
    }

    @Override
    public int messageRead(String friendEmail, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("friendEmail", friendEmail);
        map.put("username", username);
        int result = messageDAO.messageRead(map);
        return result;
    }

    @Override
    public int checkMail(String friendEmail) {
        int count = messageDAO.checkMail(friendEmail);
        return count;
    }

    @Override
    public int checkMailHomePage(String username) {

        int result = messageDAO.checkMailHomePage(username);
        return result;
    }
}
