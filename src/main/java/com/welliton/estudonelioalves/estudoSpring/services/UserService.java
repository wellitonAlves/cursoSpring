package com.welliton.estudonelioalves.estudoSpring.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.welliton.estudonelioalves.estudoSpring.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}