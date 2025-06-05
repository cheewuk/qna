package com.example.bulletin_board.dto;

import com.example.bulletin_board.domain.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private String username; //id
    private String password; //비밀번호
    private String nickname; //닉네임
    private LocalDateTime createdAt;

    public UserResponseDto(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getUserPassword();
        this.nickname = userEntity.getNickname();


        if(userEntity.getCreatedAt() != null) {
            this.createdAt = userEntity.getCreatedAt();
        }


    }
}
