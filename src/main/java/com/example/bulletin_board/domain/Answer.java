package com.example.bulletin_board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor //답변을 달 엔티티
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //답글 id

    @Column(nullable = false, length = 50)
    private String author; //작성자 이름

    @Column(nullable = false, columnDefinition = "text")
    @Lob
    private String content; //답글 내용

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate; //생성 날짜

    @UpdateTimestamp
    private LocalDateTime updateDate; //업데이트 날짜




    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계
    @JoinColumn(name = "question_id", nullable = false) //답변은 반드시 질문에 속해야함
    private Question question;

    // UserEntity와의 연관관계 추가
    @ManyToOne(fetch = FetchType.LAZY) //하나의 사용자는 여러답변 작성가능
    @JoinColumn(name = "user_id", nullable = false) //외래키, 답변은 반드시 사용자에 의해 작성되어야함
    private UserEntity userEntity;

    @Builder
    public Answer(String content, Question question, LocalDateTime creationDate, LocalDateTime updateDate, UserEntity userEntity) {
        this.content = content;
        this.question = question;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.userEntity = userEntity;

    }

    // 연관관계 편의 메소드
    public void setQuestion(Question question) {
        this.question = question;
    }

    //댓글 수정
    public void updateContent(String content) {
        this.content = content;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
