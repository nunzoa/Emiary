package com.emiary.service;

import com.emiary.domain.Diaries;

import java.util.List;

public interface DiaryService {

	int write(Diaries diaries);

	Diaries read(String dayString, String username);

	List<Diaries> checkDiary(String username);

    int modalCheck(String dateForOne);
}
