package com.example.bulletin_board.config;

import com.example.bulletin_board.util.JwtUtil;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public SpringConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // --- 모든 요청을 허용하는 가장 단순한 SecurityFilterChain ---
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 메소드명 원래대로
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                       new AntPathRequestMatcher("/"),
                                        new AntPathRequestMatcher("/login"), // GET /login 허용 (커스텀 로그인 페이지 사용 시)
                                        new AntPathRequestMatcher("/api/users/signup", HttpMethod.POST.name()),
                                        new AntPathRequestMatcher("/api/users/login", HttpMethod.POST.name()), // formLogin().loginProcessingUrl("/login")과 중복될 수 있으니 주의
                                        new AntPathRequestMatcher("/api/questions", HttpMethod.POST.name()),
                                        new AntPathRequestMatcher("/api/questions", HttpMethod.GET.name()),
                                        new AntPathRequestMatcher("/api/questions/{id}", HttpMethod.GET.name()),
                                        PathRequest.toH2Console()
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}