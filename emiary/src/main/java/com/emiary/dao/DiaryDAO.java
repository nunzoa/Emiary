package com.emiary.dao;

import com.emiary.domain.Reply;
import com.emiary.domain.ReplyAlarm;
import org.apache.ibatis.annotations.Mapper;

import com.emiary.domain.Diaries;

import java.util.List;
import java.util.Map;

@Mapper
public interface DiaryDAO {
	//다이어리 작성
	int write(Diaries diaries);

	Diaries readDiary(Map<String, String> map);

	List<Diaries> checkDiary(String username);

	int modalCheck(Map<String, String> map);

    int deleteDiary(Map<String, String> map);

    int emptyDiaryCheck(Map<String, String> map);

	Diaries writtenDiaryCheck(Map<String, String> map);

    List<Diaries> findingContent(Map<String, String> map);

    int heartStatus(Map<String, String> map);

	int inputComment(Map<String, String> map);

	List<Reply> getReply(String diaryId);

	String getNickName(String writerEmail);

    List<ReplyAlarm> getReplyAlarm(Map<String, String> map);

	int readComment(int diaryId);

    int updateAIaddr(Diaries diaries);
}
