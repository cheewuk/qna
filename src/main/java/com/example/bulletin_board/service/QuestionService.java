package com.example.bulletin_board.service;

import com.example.bulletin_board.domain.Question;
import com.example.bulletin_board.dto.QuestionRequestDto;
import com.example.bulletin_board.dto.QuestionResponseDto;
import com.example.bulletin_board.repository.QuestionRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    //모든 질문들을 조회
    @Transactional(readOnly = true)
    public List<QuestionResponseDto> getAllQuestions() {

        // Repository를 통해 Question 엔티티를 조회후 findAll()을 통해 정렬

        List<Question> questions = questionRepository.findAll();

        //조회된 Question 엔티티 리스트를 QuestionResponseDto 리스트로 변환함
        List<QuestionResponseDto> questionResponseDtos = questions.stream()
                .map(question -> new QuestionResponseDto(question))
                .collect(Collectors.toList());

        return questionResponseDtos;
    }


    //새로운 질문을 등록
    @Transactional
    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto) {

        //QuestionRequestDto로부터 Question 엔티티를 생성
        Question newQuestion = Question.builder()
                .questionTitle(questionRequestDto.getQuestionTitle())
                .questionContent(questionRequestDto.getQuestionContent())
                .author(questionRequestDto.getAuthor())
                .build();

        //createdAt와 updatedAt은 자동생성됨

        Question savedQuestion = questionRepository.save(newQuestion);

        return new QuestionResponseDto(savedQuestion);
    }


    //ID로 질문을 조회
    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestionById(Long id){
        Question question = questionRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 ID의 질문을 찾을수없습니다"));

        return new QuestionResponseDto(question);

    }

    //질문 수정
    @Transactional
    public QuestionResponseDto updateQuestion(Long id, QuestionRequestDto questionRequestDto) {

        Question question = questionRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 ID의 질문을 찾을수없습니다."));

        question.update(questionRequestDto.getQuestionTitle(),
                        questionRequestDto.getQuestionContent());

        return new QuestionResponseDto(question);
    }


    //질문 삭제
    @Transactional
    public void deleteQuestion(Long id) {

        if(!questionRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID의 질문을 찾을수없습니다");
        }
        questionRepository.deleteById(id);
    }

}
