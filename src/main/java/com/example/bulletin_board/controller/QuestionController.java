package com.example.bulletin_board.controller;

import ch.qos.logback.core.model.Model;
import com.example.bulletin_board.domain.Question;
import com.example.bulletin_board.dto.QuestionRequestDto;
import com.example.bulletin_board.dto.QuestionResponseDto;
import com.example.bulletin_board.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 현재 있는 모든 qna를 보여줌
    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getQuestions(){

         List<QuestionResponseDto> questions = questionService.getAllQuestions();

         return ResponseEntity.ok(questions);
    }


    // id로 해당하는 게시글을 검색하게 해줌
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable Long id){
        QuestionResponseDto question = questionService.getQuestionById(id);

        return ResponseEntity.ok(question);
    }

    //새 질문을 등록하는 API
    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion
            (@RequestBody QuestionRequestDto questionRequestDto){

        QuestionResponseDto createdQuestionDto =
                questionService.createQuestion(questionRequestDto);

        return
                ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDto);
    }

    //질문을 수정하는 API
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion
    (@PathVariable Long id, @RequestBody QuestionRequestDto requestDto){

        QuestionResponseDto updatedQuestionDto = questionService.updateQuestion(id, requestDto);
        return ResponseEntity.ok(updatedQuestionDto);
    }

    //질문을 삭제하는 API
    @DeleteMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build(); //HTTP 204 No Content
    }


}
