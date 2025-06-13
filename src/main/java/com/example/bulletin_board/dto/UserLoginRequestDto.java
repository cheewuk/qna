package com.example.bulletin_board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {

    private String username;
    private String password;
}
