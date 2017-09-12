package com.lan.exam.service;

import com.lan.exam.dao.QuestionMapper;
import com.lan.exam.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public List<Question> getQuestionList() {
        return questionMapper.getQuestionList();
    }

    @Override
    public Question getQuestionById(Integer questionId) {
        return questionMapper.getQuestionById(questionId);
    }

    @Override
    @Transactional
    public int deleteQuestion(Integer questionId) {
        try {
            return questionMapper.deleteQuestion(questionId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public int addQuestion(Question question){
        try {
            questionMapper.insertSelective(question);
            return question.getQuestionId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int insertList(List<Question> questions){
        return questionMapper.insertList(questions);
    }

    @Transactional
    public int update(Question question){
        try {
            return questionMapper.update(question);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



}
