package com.example.bulletin_board.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil") //로깅을 위한 Lombok 어노테이션
@Component // 이 클래스를 Spring 빈으로 등록하여 다른곳에서 주입받아 사용할수있도록 함
public class JwtUtil {

    //application.properties에서 설정한 값 주입
    @Value("${jwt.secret.key}")
    private String secretKeyPlain; //평문 시크릿 키

    @Value("${jwt.token.expiration-time-ms}")
    private long tokenExpirationTimeMs;

    private Key secretKey; //실제 사용할 Key 객체

    public static final String AUTHORIZATION_HEADER = "Authorization"; //HTTP 헤더에서 토큰을 담을 키

    public static final String BEARER_PREFIX = "Bearer "; //토큰 타입 (Bearer)

    @PostConstruct //의존성 주입이 완료된후 실행되어야함
    public void init(){
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyPlain);; //평문 시크릿 키를 바이트 배열로 디코딩

        this.secretKey = Keys.hmacShaKeyFor(keyBytes); //바이트배열을 사용하여 HMAC-SHA 키 생성
    }


    public String createToken(String username){
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenExpirationTimeMs); //만료시간 설정

        return Jwts.builder()
                .setSubject(username) //토큰의 주체
                .setIssuedAt(now) //토큰 발급 시간
                .setExpiration(validity) //토큰 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) //서명 알고리즘 및 비밀키
                .compact(); //JWT 문자열로 접속
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    //JWT의 만료시간을 확인함
    private Boolean isTokenExpired(String token){
        final Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    //JWT의 유효성을 검증
    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true; // 서명 및 만료 시간 모두 유효
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String resolveToken(String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)){
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }




}
