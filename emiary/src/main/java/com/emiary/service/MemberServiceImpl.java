package com.emiary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emiary.dao.MemberDAO;
import com.emiary.domain.Member;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO dao;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public int insert(Member member) {
		//비밀번호 암호화
		String pw = encoder.encode(member.getMemberpw());
		member.setMemberpw(pw);
		log.debug("service member값 {}", member);
		int n = dao.insert(member);
		log.debug("service n값 {}", n);
		return n;
	}

	@Override
	public boolean emailcheck(String email) {
		
		return dao.select(email) != null;
	}

	@Override
	public Member getMember(String email) {
		Member member = dao.select(email);
		return member;
	}




}
