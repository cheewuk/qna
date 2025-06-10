package com.example.bulletin_board.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.PasswordManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //이 클래스가 스프링의 설정 정보를 담고있음
@EnableWebSecurity //Spring Security 활성화
public class SpringConfig {

    @Bean //스프링 컨테이너 빈으로 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/"), //루트 경로
                                new AntPathRequestMatcher(("/api/users/login")),
                                new AntPathRequestMatcher("/api/users/signup", HttpMethod.POST.name()), //회원가입 API
                                new AntPathRequestMatcher("/api/questions", HttpMethod.GET.name()),//질문목록 조회 API
                                new AntPathRequestMatcher("/api/questions/{id}", HttpMethod.GET.name()), //질문 상세 조회 API
                                PathRequest.toH2Console()
                        ).permitAll() //위 경로들에 대해서는 모든 사용자의 접근을 허용
                        .anyRequest().authenticated() //그 외의 모든 요청은 인증된 사용자만 접근을 허용
        )
                //폼 기반 로그인 설정
                .formLogin(formLogin->
                        formLogin
                                // .loginPage("/login") 커스텀 로그인 페이지 URL
                                // 이 URL은 GET 요청시 로그인 폼을 보여주고, POST 요청시 loginProcessUrl과 동일하다면 로그인을 처리
                                // 명시하지 않을시 Spring Security가 기본 로그인 페이지를 제공
                                .loginProcessingUrl("/login") //로그인 요청을 처리할  URL,HTML form의 action과 일치해야함
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/",true)
                                .failureUrl("/login?error=true") //로그인 실패시 리다이렉트될 URL
                                .permitAll() //로그인 페이지는 사용자 모두가 접근이 가능해야함
                )
                //로그아웃 설정
                .logout(logout->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/") //로그아웃 성공시 이동할 URL
                                .invalidateHttpSession(true) //세션 무효화
                                .deleteCookies("JSESSIONID") //특정 쿠키 삭제
                                .permitAll()
                )
                .headers(headers->headers
                        .frameOptions(frameOptions->frameOptions.sameOrigin())
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    // PasswordEncoder 빈 추가
    @Bean
    public PasswordEncoder passwordEncoder(){
        //BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 구현체중하나
        //Salting과 Key Stretching을 사용해 강력한 암호화 제공

        return new BCryptPasswordEncoder();
    }
}
