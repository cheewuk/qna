package com.example.bulletin_board.dto;

import com.example.bulletin_board.domain.Question;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Getter

public class QuestionResponseDto {

    private Long id;
    private String questionTitle;
    private String questionContent;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    //Entity를 DTO로 변환해주는 생성자
    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.questionTitle = question.getQuestionTitle();
        this.questionContent = question.getQuestionContent();
        this.author = question.getAuthor();
        this.createDate = question.getCreatedAt();
        this.updateDate = question.getUpdatedAt();
    }
}
