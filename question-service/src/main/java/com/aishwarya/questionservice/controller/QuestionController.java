package com.aishwarya.questionservice.controller;

import com.aishwarya.questionservice.model.Question;
import com.aishwarya.questionservice.model.QuestionWrapper;
import com.aishwarya.questionservice.model.Response;
import com.aishwarya.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")

public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){

        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){

        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);

    }
    @GetMapping("generate")

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,@RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);

    }
    @PostMapping("getQuestions")
     public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestParam List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);

    }

    @PostMapping("getScore")

    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
    return questionService.getScore(responses);
    }
}
