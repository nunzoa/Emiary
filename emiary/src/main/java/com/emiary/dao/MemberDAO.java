package com.emiary.dao;

import org.apache.ibatis.annotations.Mapper;

import com.emiary.domain.Member;

@Mapper
public interface MemberDAO {
	//회원정보 저장
	int insert(Member member);
	//회원정보 조회
	Member select(String email);
	//회원정보 수정
	int update(Member member);


}
