package com.emiary.dao;

import org.apache.ibatis.annotations.Mapper;

import com.emiary.domain.Diaries;

@Mapper
public interface DiaryDAO {
	//다이어리 작성
	int write(Diaries diaries);

}
