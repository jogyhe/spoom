package com.lan.exam.model;

import java.io.Serializable;

/**
 * package com.lan.exam.model
 *
 * @author lanzongxiao
 * @date 2017/8/8
 */
public class Subject implements Serializable {
    private static final long serialVersionUID = -6401278604868172038L;

    private Integer subjectId;
    private String subjectName;

    private Integer sectionNum;

    public Subject(){

    }

    public Subject(String subjectName){
        this.subjectName=subjectName;
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

    public Integer getSectionNum() {
        return sectionNum;
    }

    public void setSectionNum(Integer sectionNum) {
        this.sectionNum = sectionNum;
    }
}
