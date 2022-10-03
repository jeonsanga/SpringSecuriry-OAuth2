package com.example.restapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.restapi.entity.ClubMember;
import com.example.restapi.entity.ClubMemberRole;

@SpringBootTest
class ClubMemberRepositoryTest {
	
	@Autowired
	private ClubMemberRepository clubMemberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//@Test
	void test() {
		IntStream.rangeClosed(1, 100).forEach(i->{
			ClubMember clubMember = ClubMember.builder()
											  .email("user"+i+"@zerock.org")
											  .name("사용자"+i)
											  .fromSocial(false)
											  .password(passwordEncoder.encode("1111"))
											  .build();
			
			clubMember.addMemberRole(ClubMemberRole.USER);
			if(i>80) {
				clubMember.addMemberRole(ClubMemberRole.MANAGER);
			}if(i>90) {
				clubMember.addMemberRole(ClubMemberRole.ADMIN);
			}
			
			clubMemberRepository.save(clubMember);
		});
	}
	
	@Test
	public void testRead() {
		
		//findByemail : 이런식으로 단일객체를 찾을때는 optional을 이용해서 찾는다 
		Optional<ClubMember>result = clubMemberRepository.findByemail("user95@zerock.org", false);
		
		ClubMember clubMember = result.get();
		
		System.out.println(clubMember);
	}

}
