package com.emiary.dao;

import org.apache.ibatis.annotations.Mapper;

import com.emiary.domain.User;

@Mapper
public interface UserDAO {
	//회원정보 저장
	int insert(User user);
	//회원정보 조회
	User select(String email);
	//회원정보 수정
	int update(User user);
}
