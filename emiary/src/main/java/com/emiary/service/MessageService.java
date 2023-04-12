package com.emiary.service;

import com.emiary.domain.Message;

import java.util.List;

public interface MessageService {

    List<Message> getMessageList(String friendEmail, String username);

    int sendMessage(String content, String receiverEmail, String username);
}
