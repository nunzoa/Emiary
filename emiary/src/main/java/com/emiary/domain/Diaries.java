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
	double emotionscore; // 감정지수
	String keyword;         // 빈출단어
	String created_at; // 작성 날짜
	String updated_at; // 수정 날짜

	
}
