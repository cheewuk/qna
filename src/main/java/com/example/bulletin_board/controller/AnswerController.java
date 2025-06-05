package com.example.bulletin_board.controller;

import com.example.bulletin_board.dto.AnswerRequestDto;
import com.example.bulletin_board.dto.AnswerResponseDto;
import com.example.bulletin_board.repository.AnswerRepository;
import com.example.bulletin_board.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    //질문번호에 대한 모든 댓글 조회
    @GetMapping("/questions/{questionId}/answers")
    public ResponseEntity<List<AnswerResponseDto>> getAnswers(@PathVariable Long questionId) {

        List<AnswerResponseDto> answers = answerService.getAllAnswerById(questionId);

        return ResponseEntity.ok(answers);
    }

    //새 답변을 등록
    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<AnswerResponseDto> createAnswers(@PathVariable Long questionId, @RequestBody AnswerRequestDto requestDto){

        AnswerResponseDto createdAnswer = answerService.createAnswer(questionId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    //답변 수정
    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<AnswerResponseDto> updateAnswers(@PathVariable Long questionId,
                                        @PathVariable Long answerId,
                                        @RequestBody AnswerRequestDto requestDto){
        AnswerResponseDto updatedAnswer = answerService.updateAnswer(questionId, answerId, requestDto);
        return ResponseEntity.ok(updatedAnswer);
    }

    //답변 삭제
    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswers(@PathVariable Long questionId,
                                              @PathVariable Long answerId){
        answerService.deleteAnswer(questionId, answerId);
        return ResponseEntity.noContent().build();
    }
}
