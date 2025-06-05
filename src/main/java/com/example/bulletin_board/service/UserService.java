package com.example.bulletin_board.service;

import com.example.bulletin_board.domain.UserEntity;
import com.example.bulletin_board.dto.UserRequestDto;
import com.example.bulletin_board.dto.UserResponseDto;
import com.example.bulletin_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

        //비밀번호 암호화 (17일차에 PasswordEncoder로 구현예정)


        //User 엔티티 생성
        UserEntity newUser = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .userPassword(userRequestDto.getUserPassword())
                .nickname(userRequestDto.getNickname())
                .build();

        UserEntity savedUser = userRepository.save(newUser);

        return new UserResponseDto(savedUser);
    }


}
