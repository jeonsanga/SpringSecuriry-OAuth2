package com.example.restapi.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.restapi.dto.ClubAuthMemberDTO;
import com.example.restapi.entity.ClubMember;
import com.example.restapi.entity.ClubMemberRole;
import com.example.restapi.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService{
	
	private final ClubMemberRepository clubMemberRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		log.info("------------------------------------------");
		log.info("userRequest:"+userRequest);
		
		String clientName = userRequest.getClientRegistration().getClientName();
		log.info("clientName:"+clientName);
		log.info(userRequest.getAdditionalParameters());
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("+++++++++++++++++++++++++++++++++++++++++++++");
		
		oAuth2User.getAttributes().forEach((k,v)->{
			log.info(k+":"+v);
		});
		
		String eamil = null;
		if(clientName.equals("Google")) {
			eamil = oAuth2User.getAttribute("email");
		}
		
		ClubMember member = aveSocialMember(eamil);
		
		ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(member.getEmail(),
				member.getPassword(), true, member.getRoleSet().stream().map(role->
				new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList()),oAuth2User.getAttributes());
		clubAuthMemberDTO.setName(member.getName());
				
		
		
		
		return clubAuthMemberDTO;
	}

	private ClubMember aveSocialMember(String eamil) {
		Optional<ClubMember> result = clubMemberRepository.findByemail(eamil, true);
		
		if(result.isPresent()) {
			return result.get();
		}
		
		ClubMember clubMember = ClubMember.builder().email(eamil)
													.name(eamil)
													.password(passwordEncoder.encode("1111"))
													.fromSocial(true)
													.build();
		clubMember.addMemberRole(ClubMemberRole.USER);
		
		clubMemberRepository.save(clubMember);
		return clubMember;
	}

}
