package com.example.bulletin_board.dto;

import com.example.bulletin_board.domain.Question;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter

public class QuestionResponseDto {

    private Long id;
    private String questionTitle;
    private String questionContent;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<AnswerResponseDto> answers; //질문 조회시 답변목록도 함께 변환

    //Entity를 DTO로 변환해주는 생성자
    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.questionTitle = question.getQuestionTitle();
        this.questionContent = question.getQuestionContent();
        if(question.getUserEntity() != null){
            this.author = question.getUserEntity().getUsername();
        }
        else{
            this.author = "알 수 없는 사용자";
        }
        this.createDate = question.getCreatedAt();
        this.updateDate = question.getUpdatedAt();

        //선택적 답변 목록도 DTO로 변환해서 설정
        if(question.getAnswers() != null){
            this.answers = question.getAnswers().stream()
                    .map(AnswerResponseDto::new)
                    .collect(Collectors.toList());
        }
    }
}
