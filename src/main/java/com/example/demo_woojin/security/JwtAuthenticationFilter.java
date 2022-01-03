package com.example.demo_woojin.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//이 JwtAuthenticationFilter 를 사용하라고 알려주려면 Config 패키지 아래 WebSecurityConfig 클래스를 생성해야하낟.

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            log.info("Filter is Running");

            // 토큰 검사하기. JWT 이므로 인가 서버에 요청하지 않고도 검증 가능
            if(token !=null && !token.equalsIgnoreCase("null")){
                //userId 가져오기, 위조된 경우 예외 처리된다. // String userId로 했기 때문에 TodoController에서도 보면 타입이 같다.
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated User ID :"+ userId);

                //인증 완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                //authentication 에 사용자의 인증 정보를 저정한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId,null, AuthorityUtils.NO_AUTHORITIES);
                //여기의 첫 매개변수 userId가 TodoController에서의 @AuthenticationPrincipal에 해당하는 것.



                //securityContext에 인증된 사용자를 등록한다.
                //이제 스프링은 컨트롤러 메서드를 부를때 @AuthenticationPrincipal 어노테이션이 있다는 것을 안다 그리고 (1)에 이어서,
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Holder를 이용해 SecurityContext를 생성하고 생성한 곳에 인증 정보인 authentication을 넣어 다시 Holder에 컨텍스트로 등록한다.
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();


                securityContext.setAuthentication(authentication);// (1) UsernamePasswordAuthenticationToken 오브젝트를 가져온다
                SecurityContextHolder.setContext(securityContext);


            }
        }catch(Exception ex){
            logger.error("could not set user Authentication in security context", ex);
        }
        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        //Http 요청의 헤더를 Parsing 하여 Bearer토큰을 리턴한다.

        String bearerToken =request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
