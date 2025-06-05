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
    //임시
    private Long userId; // 질문을 작성한 사용자의 ID
                         // 클라이언트에서 이 값을 입력해서 어떠 ㄴ사용자가 작성했는지 지정

}
