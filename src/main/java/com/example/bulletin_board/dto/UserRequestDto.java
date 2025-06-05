package com.example.bulletin_board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String username; //id
    private String userPassword; //비밀번호
    private String nickname; //닉네임
}
