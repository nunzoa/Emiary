package com.emiary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    int message_id;
    String sender_email;
    String receiver_email;
    String content;
    String sent_at;
    String read_at;
    String nickname;
    String imgurl;
}
