package com.example.restapi.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class PasswordTests {
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void testEncoder() {
		String password = "1111";
		
		String enPw = passwordEncoder.encode(password);
		
		System.out.println("enPw:"+enPw);
		
		boolean matchResult = passwordEncoder.matches(password, enPw);
		
		System.out.println("matchResult:"+matchResult);
		
	}

}
