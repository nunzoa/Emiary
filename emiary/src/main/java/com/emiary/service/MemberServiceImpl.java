package com.emiary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emiary.dao.MemberDAO;
import com.emiary.domain.Member;

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
		
		int n = dao.insert(member);
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
