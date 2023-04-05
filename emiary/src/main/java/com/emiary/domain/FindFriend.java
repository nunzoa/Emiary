package com.emiary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindFriend {
    String email;
    String nickname;
    String img;
    int countDiary;
    String emotion;
}

