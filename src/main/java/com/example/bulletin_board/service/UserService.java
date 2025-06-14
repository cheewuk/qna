package com.example.bulletin_board.service;

import com.example.bulletin_board.domain.UserEntity;
import com.example.bulletin_board.dto.UserLoginRequestDto;
import com.example.bulletin_board.dto.UserRequestDto;
import com.example.bulletin_board.dto.UserResponseDto;
import com.example.bulletin_board.repository.UserRepository;
import com.example.bulletin_board.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //새로운 사용자 등록
    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {

        //아이디 중복체크
        if(userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        //닉네임 중복체크
        if(userRepository.existsByNickname(userRequestDto.getNickname())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRequestDto.getUserPassword());


        //User 엔티티 생성
        UserEntity newUser = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .userPassword(encodedPassword)
                .nickname(userRequestDto.getNickname())
                .build();

        UserEntity savedUser = userRepository.save(newUser);

        //로그인 로직은 18일차에 PasswordEncoder를 사용하여 비밀번호 검증 예쩡

        return new UserResponseDto(savedUser);
    }

    @Transactional
    public String login(UserLoginRequestDto userLoginRequestDto) {
        //사용자의 아이디와 비밀번호를 사용하여 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword());

        //실제 검증은 AuthenticationManager를 통해 UserDetailsService 및 PasswordEncoder가 처리
        //authenticationManagerBuilder를 사용하여 AuthenticationManager를 얻고 authenticate() 호출
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //인증된 사용자 정보를 기반으로 JWT 생성
        String jwt = jwtUtil.createToken(authentication.getName());

        return jwt;
    }


}
