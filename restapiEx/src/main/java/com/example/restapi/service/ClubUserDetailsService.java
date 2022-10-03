package com.example.restapi.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.restapi.dto.ClubAuthMemberDTO;
import com.example.restapi.entity.ClubMember;
import com.example.restapi.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService{
	
	private final ClubMemberRepository clubMemberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("ClubUserDetailsService loadUserByUsername"+username);
		
		Optional<ClubMember> result = clubMemberRepository.findByemail(username, false);
		
		
		if(result.isEmpty()) {
			log.info("1111111");
			throw new UsernameNotFoundException("check Email or socail");
		}
		
		ClubMember clubMember = result.get();
		
		log.info("____________________________________");
		log.info(clubMember);
		
		ClubAuthMemberDTO clAuthMember = new ClubAuthMemberDTO(clubMember.getEmail(), clubMember.getPassword(), clubMember.isFromSocial(),
				clubMember.getRoleSet().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet()));
				clAuthMember.setName(clAuthMember.getName());
				clAuthMember.setFromSocial(clubMember.isFromSocial());
		return clAuthMember;
	}

}
