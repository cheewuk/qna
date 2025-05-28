package com.example.bulletin_board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor

public class Question {

    //질문을 등록하기위한 질문 ID, 질문 제목, 질문 내용, 작성자가 필요함

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //게시글 ID가 자동으로 생성
    private Long id; //게시글 ID

    @Column(nullable = false, length = 200) //제목은 null이 불가하며, 길이는 200자로 제한
    private String questionTitle; //질문 제목

    @Lob//(CLOB 타입으로 긴내용을 매핑)
    @Column(nullable = false, columnDefinition = "TEXT") //텍스트타입을 명시해주며, null이 불가하다
    private String questionContent; //게시글 내용

    @Column(nullable = false, length = 50) //작성자이름은 null이 불가하며, 길이는 50자로 제한
    private String author; //작성자

    @CreationTimestamp //엔티티 생성시 현재 시간을 자동저장해줌
    @Column(updatable = false) //엔티티 생성시 시간이므로 업데이트할수없음
    private LocalDateTime createdAt;

    @UpdateTimestamp //엔티티 수정 시 현재시간을 자동저장해줌
    private LocalDateTime updatedAt;



    @Builder
    public Question(String questionTitle, String questionContent, String author) {
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.author = author;
    }

    //Setter를 최소화
    public void update(String questionTitle, String questionContent){
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
    }

}
