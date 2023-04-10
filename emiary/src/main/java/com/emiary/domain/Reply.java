package com.emiary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    int reply_id;
    int diary_id;
    String replyContent;
    String writer_email;
    String created_at_reply;
    String smile;
    String nickname;
}
