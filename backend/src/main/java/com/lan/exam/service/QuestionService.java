package com.lan.exam.service;

import com.lan.exam.model.Question;

import java.util.List;

public interface QuestionService{

    List<Question> getQuestionList();

    Question getQuestionById(Integer questionId);

    int deleteQuestion(Integer questionId);

    int addQuestion(Question question);

    int insertList(List<Question> questions);

    int update(Question question);



}
