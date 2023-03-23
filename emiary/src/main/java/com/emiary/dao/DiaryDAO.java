package com.emiary.dao;

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
}
