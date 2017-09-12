package com.lan.exam.service;

import com.lan.exam.dao.SectionMapper;
import com.lan.exam.model.Section;
import com.lan.exam.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService{

    @Autowired
    private SectionMapper sectionMapper;

    @Override
    public List<Section> getAllSection() {
        return sectionMapper.getAllSection();
    }

    @Override
    public List<Subject> getAllSubject() {
        return sectionMapper.getAllSubject();
    }

    @Override
    public List<Section> getSectionsBySubjectId(Integer subjectId) {
        return sectionMapper.getSectionsBySubjectId(subjectId);
    }

    @Override
    @Transactional
    public int insertSubject(Subject subject) {
        try {
            return sectionMapper.insertSubject(subject);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int insertSection(Section section) {
        try {
            return sectionMapper.insertSection(section);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int updateSubject(Subject subject) {
        try {
            return sectionMapper.updateSubject(subject);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int updateSection(Section section) {
        try {
            return sectionMapper.updateSection(section);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int deleteSubject(Integer subjectId) {
        try {
            return sectionMapper.deleteSubject(subjectId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int deleteSection(Integer sectionId) {
        try {
            return sectionMapper.deleteSection(sectionId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
