package com.example.bulletin_board.controller;

import com.example.bulletin_board.dto.UserLoginRequestDto;
import com.example.bulletin_board.dto.UserRequestDto;
import com.example.bulletin_board.dto.UserResponseDto;
import com.example.bulletin_board.service.UserService;
import com.example.bulletin_board.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        try{
            //응답 본문에 JWT 포함
            String jwt = userService.login(userLoginRequestDto);

            //응답 헤더에 JWT 포함
            response.setHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.BEARER_PREFIX + jwt);

            return ResponseEntity.ok("로그인 성공, JWT가 응답 헤더에 포함 완료");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }

    }
}
