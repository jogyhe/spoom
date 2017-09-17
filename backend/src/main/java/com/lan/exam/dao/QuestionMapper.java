package com.lan.exam.dao;

import com.lan.exam.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {

    List<Question> getQuestionList();

    Question getQuestionById(@Param("questionId") Integer questionId);

    int deleteQuestion(@Param("questionId") Integer questionId);

    int insertSelective(@Param("question") Question question);

    int insertList(@Param("questions") List<Question> questions);

    int update(@Param("question") Question question);
}
