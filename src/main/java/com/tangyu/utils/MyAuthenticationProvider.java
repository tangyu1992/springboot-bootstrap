package com.tangyu.utils;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.tangyu.model.MyUserDetails;
import com.tangyu.service.impl.MyUserDetailsService;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MyUserDetailsService userService;

	/**
	 * 自定义验证方式
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println(123123);
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		MyUserDetails user = (MyUserDetails) userService.loadUserByUsername(username);
		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}

		// 加密过程在这里体现
		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
