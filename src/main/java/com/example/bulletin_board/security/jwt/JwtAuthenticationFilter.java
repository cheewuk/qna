package com.example.bulletin_board.security.jwt;

import com.example.bulletin_board.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        //요청 헤더에서 JWT 추출
        String tokenValue = jwtUtil.resolveToken(request.getHeader(JwtUtil.AUTHORIZATION_HEADER));

        //토큰 유효성 검사
        if(StringUtils.hasText(tokenValue)&&jwtUtil.validateToken(tokenValue)) {
            //토큰에서 사용자 정보 추출
            String username = jwtUtil.getUsernameFromToken(tokenValue);

            try{
                //UserDetailsService를 사용하여 UserDetails 객체 가져오기
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //인증 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //SecurityContextHolder에 인증정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("사용자 '{}' 인증 성공", username);

            }
            catch (Exception e){
                log.debug("유효한 JWT 토큰이 없습니다. URI: { }", request.getRequestURI());
            }

            //다음 필터로 요청 전달
            filterChain.doFilter(request, response);
        }
    }
}
