package com.example.demo_woojin.config;

import com.example.demo_woojin.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //HttpSecurity 는 시큐리티 설정을위한 오브젝트
        //http 시큐리티 빌더

        http.cors() //WebMvcConfig 에서 이미 설정했으므로 기본 cors설정
                .and()
                .csrf() //csrf는 사용안하니 disabled
                .disable()
                .httpBasic()    //token 사용하니까 basic인증은 disable
                .disable()
                .sessionManagement()        //세션 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() //   /와  /auth/** 경로는 인증 안해도 된다.
                .antMatchers("/", "/auth/**").permitAll()
                .anyRequest()       //    /와  /auth/** 이외의 경로는 모두 인증해야한다.
                .authenticated();

        //filter등록
        //매 요청마다 CorsFilter 실행한 후에 jwtAuthenticationFilter실행한다.
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    }
}
