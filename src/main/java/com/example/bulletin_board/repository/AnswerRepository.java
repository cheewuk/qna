package com.example.bulletin_board.repository;

import com.example.bulletin_board.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    //특정 Question ID에 해당하는 모든 Answer를 찾는 메소드
    List<Answer> findByQuestionId(Long questionId);

    //정렬하고싶다면
    List<Answer> findByQuestionIdOrderByCreationDateAsc(Long questionId); //생성일 오름차순(생성일순)
    List<Answer> findByQuestionIdOrderByCreationDateDesc(Long questionId); // 생성일 내림차순(최신순)


}
