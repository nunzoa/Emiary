package com.emiary.controller;

import com.emiary.domain.Member;
import com.emiary.domain.Message;
import com.emiary.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageService messageService;


    @GetMapping("to")
    public String messageTO(){
        return "messageView/messageForm";
    }

    @ResponseBody
    @GetMapping("getMessageList")
    public List<Message> getMessageList(String friendEmail, @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("friendEmail : {}, userDetails : {}", friendEmail, userDetails.getUsername());
        List<Message> messageList = messageService.getMessageList(friendEmail, userDetails.getUsername());

        log.debug("messageList : {}", messageList);
        return messageList;
    }



    @ResponseBody
    @GetMapping("sendMessage")
    public int sendMessage(String content, String receiver_email, @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("receiver_email : {}, userDetails.getUsername() : {}", receiver_email, userDetails.getUsername());
        int result = messageService.sendMessage(content, receiver_email, userDetails.getUsername());

        return result;
    }

    @ResponseBody
    @GetMapping("getFriendProfile")
    public Member getFriendProfile(String friendEmail){
        Member member = messageService.getProfile(friendEmail);
        return member;
    }


    @ResponseBody
    @GetMapping("messageRead")
    public int messageRead(String friend_email, @AuthenticationPrincipal UserDetails userDetails) {
        int result = messageService.messageRead(friend_email, userDetails.getUsername());
        return result;
    }

    @ResponseBody
    @GetMapping("checkMail")
    public int checkMail(String friend_email){
        int count = messageService.checkMail(friend_email);
        return count;
    }

    @ResponseBody
    @GetMapping("checkMailHomePage")
    public int checkMailHomePage(@AuthenticationPrincipal UserDetails userDetails) {
        int result = messageService.checkMailHomePage(userDetails.getUsername());
        log.debug("메시지가 왜 뜨냐 {}", result);
        return result;
    }

}
