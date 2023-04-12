package com.emiary.service;

import com.emiary.dao.MessageDAO;
import com.emiary.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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

        int result = messageDAO.sendMessage(map);

        return result;
    }
}
