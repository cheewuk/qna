package com.example.bulletin_board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequestDto {

    private String content;
    private Long userId;
}
