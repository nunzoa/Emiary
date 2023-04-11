package com.emiary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor	
public class Diaries {
	
	int diary_id;   // 다이어리 ID
	String email;   // 다이어리를 작성한 ID
	String content; // 본문
	String content_notag; // 태그 안 붙어있는 본문(검색을 위해)
	double emotionscore; // 감정지수
	String keyword;         // 빈출단어
	String created_at; // 작성 날짜
	String updated_at; // 수정 날짜
	String wordsforai;
	String aiIMG;

	//visited
	String heart;
	String visited_email;
	int cntHeart;

	//reply
	int reply_id;
	String replyContent;
	char smile;
	String created_at_reply;

	
}
