package com.emiary.service;

import com.emiary.domain.Member;

/**
 * 회원정보 관련 처리
 */
public interface MemberService {
	/**
	 * 회원정보 저장 (가입)
	 * @param member 가입양식에서 전달된 회원정보
	 * @return 저장된 개수
	 */
	public int insert(Member member);
	
	/**
	 * 아이디 존재 확인
	 * @param id 찾을 아이디
	 * @return	해당 아이디 존재 여부 (있으면 true)
	 */
	public boolean emailcheck(String email);
	
	/**
	 * 아이디로 회원 정보 찾기
	 * @param id 검색할 아이디
	 * @return	해당 회원의 정보
	 */
	public Member getMember(String email);


}
