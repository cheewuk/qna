package com.example.bulletin_board.dto;

import com.example.bulletin_board.domain.Answer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AnswerResponseDto {

    private Long id;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Long questionId;

    public AnswerResponseDto(Answer answer){
        this.id = answer.getId();
        this.content = answer.getContent();
        if(answer.getUserEntity() != null){ //UserEntity가 null이 아닐 때만 접근
            this.author = answer.getUserEntity().getNickname(); //UserEntity에서 닉네임 가져오기
        }
        else{
            this.author = "알수없는 사용자";
        }
        this.author = answer.getAuthor();
        this.createDate = answer.getCreationDate();
        this.updateDate = answer.getUpdateDate();

        if(answer.getQuestion() != null){
            this.questionId = answer.getQuestion().getId();
        }
    }
}
