package com.example.restapi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.restapi.dto.ClubAuthMemberDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/sample/")
@Slf4j
public class SampleController {
	
	//누구나접근가능
	@GetMapping("/all")
	public void exAll() {
		log.info("Exall");
	}
	
	//로그인한 사람만 접근가능
	@GetMapping("/member")
	public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
		log.info("exMember");
		log.info("-----------------------------");
		log.info("clubAuthMemberDTO:",clubAuthMemberDTO);
	}
	//관리자만 접근가능
	@GetMapping("/admin")
	public void exAdmin() {
		log.info("exAdmin");
	}

}
