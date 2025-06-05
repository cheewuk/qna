package com.example.bulletin_board.controller;

import com.example.bulletin_board.dto.UserRequestDto;
import com.example.bulletin_board.dto.UserResponseDto;
import com.example.bulletin_board.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    //회원가입로직
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup (@RequestBody UserRequestDto userRequestDto) {

        UserResponseDto response = userService.signup(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
