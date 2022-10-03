package com.example.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user1")
//		.password("$2a$10$mBbpDTi03nrFpOzYHVmGJutUZiUxxC29JAD1.ai/.NrFUMZv8cqJu")
//		.roles("USER");
//	}
	
	//접근제한
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/login", "/singUp", "/access_denied", "/resources/**").permitAll() // 로그인 권한은 누구나, resources파일도 모든권한
                // USER, ADMIN 접근 허용
                .antMatchers("/h2-console/*").permitAll() 
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER")
                .and()
            .formLogin()
               // .loginPage("/login")  //로그인페이지
                //.loginProcessingUrl("/login_proc")  //로그인 폼과 같은주소
                .defaultSuccessUrl("/sample/member") //로그인 성공시 페이지
                .failureUrl("/access_denied") // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보낸다.
                .and()
            .csrf()
            .ignoringAntMatchers("/h2-console/*")
            .disable();
     		http.oauth2Login()
     		 .defaultSuccessUrl("/sample/member") ;
        	http.headers().frameOptions().disable();
        	http.logout();
       
        	
    }

}
