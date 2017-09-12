package com.lan.exam.dao;

import com.lan.exam.model.Section;
import com.lan.exam.model.Subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionMapper {

    List<Section> getAllSection();

    List<Subject> getAllSubject();

    List<Section> getSectionsBySubjectId(Integer subjectId);


    int insertSubject(@Param("subject") Subject subject);

    int insertSection(@Param("section") Section section);

    int updateSubject(@Param("subject") Subject subject);

    int updateSection(@Param("section") Section section);

    int deleteSubject(@Param("subjectId") Integer subjectId);

    int deleteSection(@Param("sectionId") Integer sectionId);
}
