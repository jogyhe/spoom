package com.lan.exam.model;

import java.io.Serializable;

/**
 * package com.lan.model
 *
 * @author lanzongxiao
 * @date 2017/8/8
 */
public class Section implements Serializable {

    private static final long serialVersionUID = 6361407082968872334L;

    private Integer sectionId;
    private String sectionName;
    private Integer subjectId;

    private String subjectName;

    private Integer questionNum;

    public Section(){

    }
    public Section(String sectionName){
        this.sectionName=sectionName;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }
}
