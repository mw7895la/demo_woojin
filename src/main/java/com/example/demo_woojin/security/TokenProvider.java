package com.example.demo_woojin.security;

import com.example.demo_woojin.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    //이 클래스가 하는 일은 사용자 정보를 받아 JWT를 생성하는 일.
    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";
    //private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    byte[] keyBytes = SECRET_KEY.getBytes();
    Key key = Keys.hmacShaKeyFor(keyBytes);
    // SECRET_KEY가 32byte 이하라면 compile단계에서 error 발생


    public String create(UserEntity userEntity){
        //기한은 지금부터 1일
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기위한 SECRET_KEY
                .signWith(key, SignatureAlgorithm.HS512)
                // Payload에 들어갈 내용
                .setSubject(userEntity.getId()) //sub
                .setIssuer("demo_woojin")  //iss
                .setIssuedAt(new Date())    //iat  토큰이 발행된 날짜와 시간
                .setExpiration(expiryDate).compact();
    }

    public String validateAndGetUserId(String token){
        // parseClaimsJws 메소드가 base64로 디코딩 및 파싱
        // Header 와 PayLoad를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        //위조되지 않았다면 페이로드(Claim) 리턴
        //그중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Jwts.parser()가 deprecated되어, parserBuilder로 대체
        // 참고: https://velog.io/@guswns3371/develop-feat-auth
        return claims.getSubject();
   }

}
