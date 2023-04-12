package com.emiary.service;

import com.emiary.domain.Member;
import com.emiary.domain.Message;

import java.util.List;

public interface MessageService {

    List<Message> getMessageList(String friendEmail, String username);

    int sendMessage(String content, String receiverEmail, String username);

    Member getProfile(String friendEmail);

    int messageRead(String friendEmail, String username);

    int checkMail(String friendEmail);

    int checkMailHomePage(String username);
}
