package com.example.bulletin_board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class QuestionRequestDto {


    //사용자에게 요청받을 정보
    private String questionTitle;
    private String questionContent;
    private String author;


}
