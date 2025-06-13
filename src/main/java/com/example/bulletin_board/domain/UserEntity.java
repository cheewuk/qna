package com.example.bulletin_board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "userEntity")
public class UserEntity {


    //회원 목록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 가입시 사용할 ID
    @Column(nullable = false, length = 20, unique = true)
    private String username;

    //회원 가입시 사용할 비밀번호
    @Column(nullable = false, length = 72)
    private String userPassword;

    //회원 가입시 사용할 닉네임
    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    // Question과의 양방향 연관관계
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();

    // Answer과의 양방향 연관관계
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public UserEntity(String username, String userPassword, String nickname, LocalDateTime updatedAt) {
        this.username = username;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.updatedAt = updatedAt;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setUserEntity(this);
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

}
