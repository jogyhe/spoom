package com.lan.exam.model;

import java.io.Serializable;

/**
 * package com.lan.exam.model.exam
 *
 * @author lanzongxiao
 * @date 2017/8/8
 */
public class QuestionType implements Serializable {
    private static final long serialVersionUID = 2539006284354742844L;

    private Integer questionTypeId;
    private String name;
    private Boolean subjective;

    public Integer getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Integer questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSubjective() {
        return subjective;
    }

    public void setSubjective(boolean subjective) {
        this.subjective = subjective;
    }
}
