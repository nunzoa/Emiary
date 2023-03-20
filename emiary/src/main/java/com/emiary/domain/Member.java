package com.emiary.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {
	String email;			//사용자 식별 ID
	String memberpw;			//비밀번호
	String nickname;			//사용자 이름
	String phone;				//전화번호
	String birthdate ;				//생일
	boolean enabled;			//계정 상태 (1-사용가능, 0-불가능)
	String rolename;			//('ROLE_USER' - 일반회원, 'ROLE_ADMIN' - 관리자)


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
