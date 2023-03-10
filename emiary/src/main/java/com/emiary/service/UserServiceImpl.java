package com.emiary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emiary.dao.UserDAO;
import com.emiary.domain.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO dao;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public int insert(User user) {
		//비밀번호 암호화
		String pw = encoder.encode(user.getPassword());
		user.setPassword(pw);		
		
		int n = dao.insert(user);
		return n;
	}

	@Override
	public boolean emailcheck(String email) {
		
		return dao.select(email) != null;
	}

	@Override
	public User getUser(String email) {
		User user = dao.select(email);
		return user;
	}
	


	
	

}
