package com.emiary.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {
	String email;			//사용자 식별 ID
	String memberpw;			//비밀번호
	String nickname;			//사용자 이름
	String phone;				//전화번호
	String birthdate ;				//생일
	String 	originalfile;	//첨부파일 원래이름
	String 	savedfile;		//첨부파일 서버에 저장된 이름
	String imgURL;
	boolean enabled;			//계정 상태 (1-사용가능, 0-불가능)
	String rolename;			//('ROLE_USER' - 일반회원, 'ROLE_ADMIN' - 관리자)
	List<Member> friends;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
	 
}
