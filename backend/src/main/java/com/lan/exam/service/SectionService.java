package com.lan.exam.service;

import com.lan.exam.model.Section;
import com.lan.exam.model.Subject;

import java.util.List;

public interface SectionService{

    List<Section> getAllSection();

    List<Subject> getAllSubject();

    List<Section> getSectionsBySubjectId(Integer subjectId);

    int insertSubject(Subject subject);

    int insertSection(Section section);

    int updateSubject(Subject subject);

    int updateSection(Section section);

    int deleteSubject(Integer subjectId);

    int deleteSection(Integer sectionId);

}
