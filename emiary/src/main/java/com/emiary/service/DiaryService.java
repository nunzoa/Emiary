package com.emiary.service;

import com.emiary.domain.Diaries;
import com.emiary.domain.Reply;

import java.util.List;
import java.util.Map;

public interface DiaryService {

	int write(Diaries diaries);

	Diaries read(String dayString, String username);

	List<Diaries> checkDiary(String username);

    int modalCheck(String dateForOne, String username);

    int deleteDiary(String deleteDiary, String username);

    String lastDiary(String dayString, String username);

    String nextDiary(String dayString, String username);

    Diaries lastReadDiary(String dayString, String username);

    Diaries nextReadDiary(String dayString, String username);

    List<Diaries> findingContent(String searchInput, String username);

    int heartStatus(String email, String diaryId);

    int inputComment(String comment, String diaryId, String username);

    List<Reply> getReply(String diaryId);
}
