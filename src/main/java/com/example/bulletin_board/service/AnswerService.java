package com.example.bulletin_board.service;

import com.example.bulletin_board.domain.Answer;
import com.example.bulletin_board.domain.Question;
import com.example.bulletin_board.domain.UserEntity;
import com.example.bulletin_board.dto.AnswerRequestDto;
import com.example.bulletin_board.dto.AnswerResponseDto;
import com.example.bulletin_board.repository.AnswerRepository;
import com.example.bulletin_board.repository.QuestionRepository;
import com.example.bulletin_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    //질문에 대한 모든 답변을 조회
    @Transactional
    public List<AnswerResponseDto> getAllAnswerById(Long questionId){

        if(!questionRepository.existsById(questionId)){
            throw new IllegalArgumentException("해당 ID의 질문을 찾을수없습니다.");
        }

        //AnswerRepository를 사용하여 해당 questionId에 대한 Answer들을 조회
        List<Answer> answers = answerRepository.findByQuestionIdOrderByCreationDateDesc(questionId);

        //조회된 Answer 엔티티 리스트를 AnswerResponseDto 리스트로 변환
        List<AnswerResponseDto> answerResponseDtos = answers.stream()
                .map(answer -> new AnswerResponseDto(answer))
                .collect(Collectors.toList());

        return answerResponseDtos;
    }

    //새로운 답변 생성
    @Transactional
    public AnswerResponseDto createAnswer(Long questionId, AnswerRequestDto answerRequestDto){
        //답변 달 Question 엔티티 조회
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()->new IllegalArgumentException("해당 ID의 질문을 찾을수없습니다."));

        //DTO로부터 userId를 가져와 작성자 UserEntity 조회
        UserEntity authorEntity = userRepository.findById(answerRequestDto.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("해당 ID의 사용자를 찾을수 없습니다."));


        //Answer 엔티티 생성
        Answer newAnswer = Answer.builder()
                .content(answerRequestDto.getContent())
                .question(question)
                .userEntity(authorEntity)
                .build();

        //생성된 엔티티 저장
        Answer savedAnswer = answerRepository.save(newAnswer);

        //저장된 엔티티 AnswerResponseDto로 변환해서 반환
        return new AnswerResponseDto(savedAnswer);
    }

    //답변 수정
    @Transactional
    public AnswerResponseDto updateAnswer(Long questionId, Long answerId, AnswerRequestDto answerRequestDto){
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new IllegalArgumentException("해당 ID의 질문을 찾을수없습니다."));

        Answer existingAnswer = answerRepository.findById(answerId)
                .orElseThrow(()-> new IllegalArgumentException("해당 ID의 답변을 찾을수없습니다."));

        if(!existingAnswer.getQuestion().getId().equals(questionId)){
            throw new IllegalArgumentException("요청된 답변이 해당질문에 속하지않습니다.");
        }

        if(answerRequestDto.getContent()!=null){
            existingAnswer.updateContent(answerRequestDto.getContent());
        }

        return new AnswerResponseDto(existingAnswer);
    }


    //답변 삭제
    @Transactional
    public void deleteAnswer(Long questionId, Long answerId){
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new IllegalArgumentException("해당 ID의 질문을 찾을수없습니다."));

        Answer existingAnswer = answerRepository.findById(answerId)
                .orElseThrow(()-> new IllegalArgumentException("해당 ID의 답변을 찾을수없습니다."));

        if(!existingAnswer.getQuestion().getId().equals(questionId)){
            throw new IllegalArgumentException("요청된 답변이 해당질문에 속하지않습니다.");
        }
        else{
            answerRepository.delete(existingAnswer);
        }

    }
}

