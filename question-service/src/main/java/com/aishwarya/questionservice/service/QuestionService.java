package com.aishwarya.questionservice.service;


import com.aishwarya.questionservice.dao.QuestionDao;
import com.aishwarya.questionservice.model.Question;
import com.aishwarya.questionservice.model.QuestionWrapper;
import com.aishwarya.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions(){
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Failed",HttpStatus.METHOD_NOT_ALLOWED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {

        List<Integer> questions=questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);

        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

        List<QuestionWrapper> wrappers=new ArrayList<>();

        List<Question> questions=new ArrayList<>();

        for(Integer id:questionIds){
            questions.add(questionDao.findById(id).get());

        }

        for(Question question:questions){
            QuestionWrapper qw=new QuestionWrapper(question.getId(),question.getQuestionTitle(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4());
            wrappers.add(qw);
        }


        return new ResponseEntity<>(wrappers,HttpStatus.OK);

    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;

        for(Response response:responses){
           Question question=questionDao.findById(response.getId()).get();

            if(response.getResponse().equals(question.getRightAnswer()))
                right++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);

    }
}
