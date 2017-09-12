package com.lan.exam.controller;

import com.lan.common.model.utilModel.Message;
import com.lan.common.model.utilModel.UserInfo;
import com.lan.exam.model.Question;
import com.lan.exam.model.Section;
import com.lan.exam.model.Subject;
import com.lan.exam.service.QuestionService;
import com.lan.exam.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package com.lan.exam.controller
 *
 * @author lanzongxiao
 * @date 2017/8/12
 */
@CrossOrigin(
        origins = {"http://localhost:8087"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},
        allowCredentials = "true", maxAge = 3600)
@Controller
@RequestMapping("/exam/question")
public class QuestionController {
    private final static Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SectionService sectionService;

    /**
     * 获取所有question
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Message getQuestionList() {
        Message message = new Message();
        try {
            List<Question> questions = questionService.getQuestionList();
            message.setData(questions);
            message.setMsg("获取问题列表成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("获取问题列表失败！");
        }
        return message;
    }

    /**
     * 根据id查询question
     *
     * @param questionId
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public Message getQuestionById(@PathVariable Integer questionId) {
        Message message = new Message();
        try {
            Question question = questionService.getQuestionById(questionId);
            message.setData(question);
            message.setMsg("获取问题成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("获取问题失败！");
        }
        return message;
    }

    /**
     * 添加一个question
     * 添加creator，createTime属性
     *
     * @param question
     * @param userInfo
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Message addQuestion(@RequestBody Question question, @AuthenticationPrincipal UserInfo userInfo) {
        Message message = new Message();
        try {
            question.setCreator(userInfo.getUsername());
            question.setCreateTime(new Date());
            question.setLastModify(new Date());
            questionService.addQuestion(question);
            message.setMsg("添加问题成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("添加问题失败！");
        }
        return message;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Message updateQuestion(@RequestBody Question question) {
        Message message = new Message();
        try {
            question.setLastModify(new Date());
            questionService.update(question);
            message.setMsg("更新问题成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("更新问题失败！");
        }
        return message;
    }

    /**
     * 根据id删除对应的question
     *
     * @param questionId
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/delete/{questionId}", method = RequestMethod.GET)
    public Message deleteQuestion(@PathVariable Integer questionId) {
        Message message = new Message();
        try {
            questionService.deleteQuestion(questionId);
            message.setMsg("删除问题成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("删除问题失败！");
        }
        return message;
    }

    /**
     * 把获取到的所有section打包到一个map中
     * key:subjectName
     * value:Map<Integer,String> 包含该科目section的map
     *
     * @return Map<String,Map<Integer,String>>
     */
    @ResponseBody
    @RequestMapping(value = "/section/map", method = RequestMethod.GET)
    public Message getAllSectionMap() {
        Message message = new Message();
        try {
            List<Section> sectionList = sectionService.getAllSection();
            Map<String, Map<Integer, String>> m = new HashMap<>();
            Map<Integer, String> t = new HashMap<>();
            Integer subjectId = -1;
            String subjectName = new String();
            for (Section section : sectionList) {
                if (subjectId != section.getSubjectId()) {
                    if (subjectId != -1) {
                        m.put(subjectName, t);
                        t = new HashMap<>();
                    }
                    subjectId = section.getSubjectId();
                    subjectName = section.getSubjectName();
                }
                t.put(section.getSectionId(), section.getSectionName());
            }
            m.put(subjectName, t);//添加最后一组section
            message.setData(m);
            message.setMsg("获取科目章节成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("获取科目章节失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/subjectsMap", method = RequestMethod.GET)
    public Message getSubjectsMap() {
        Message message = new Message();
        try {
            List<Subject> subjects = sectionService.getAllSubject();
            Map<Integer, Subject> subjectsMap = new HashMap<>();
            for (Subject subject : subjects) {
                subjectsMap.put(subject.getSubjectId(), subject);
            }
            message.setData(subjectsMap);
            message.setMsg("获取科目成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("获取科目失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/sectionsMap/{subjectId}", method = RequestMethod.GET)
    public Message getSectionsMap(@PathVariable Integer subjectId) {
        Message message = new Message();
        try {
            List<Section> sections = sectionService.getSectionsBySubjectId(subjectId);
            Map<Integer, Section> sectionsMap = new HashMap<>();
            for (Section section : sections) {
                sectionsMap.put(section.getSectionId(), section);
            }
            message.setData(sectionsMap);
            message.setMsg("获取章节成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("获取章节失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/subject/add", method = RequestMethod.POST)
    public Message addSubject(@RequestParam("subjectName") String subjectName) {
        Message message = new Message();
        try {
            Subject subject = new Subject(subjectName);
            sectionService.insertSubject(subject);
            subject.setSectionNum(0);
            message.setData(subject);
            message.setMsg("添加科目成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("添加科目失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/section/add", method = RequestMethod.POST)
    public Message addSection(@RequestBody Section section) {
        Message message = new Message();
        try {
            sectionService.insertSection(section);
            section.setQuestionNum(0);
            message.setData(section);
            message.setMsg("添加章节成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("获取章节失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/subject/edit", method = RequestMethod.POST)
    public Message editSubject(@RequestBody Subject subject) {
        Message message = new Message();
        try {
            sectionService.updateSubject(subject);
            message.setData(subject);
            message.setMsg("修改科目成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("修改科目失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/section/edit", method = RequestMethod.POST)
    public Message editSection(@RequestBody Section section) {
        Message message = new Message();
        try {
            sectionService.updateSection(section);
            message.setData(section);
            message.setMsg("修改章节成功！");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("修改章节失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/subject/delete/{subjectId}", method = RequestMethod.GET)
    public Message deleteSubject(@PathVariable Integer subjectId) {
        Message message = new Message();
        try {
            Integer flag = sectionService.deleteSubject(subjectId);
            if (flag == 0) {
                message.setCode(0);
                message.setMsg("删除科目失败,科目下仍有章节！");
            } else {
                message.setMsg("删除科目成功！");
            }
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("删除科目失败！");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/section/delete/{sectionId}", method = RequestMethod.GET)
    public Message deleteSection(@PathVariable Integer sectionId) {
        Message message = new Message();
        try {
            Integer flag = sectionService.deleteSection(sectionId);
            if (flag == 0) {
                message.setCode(0);
                message.setMsg("删除章节失败，章节下仍有问题！");
            } else {
                message.setMsg("删除章节成功！");
            }
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("删除章节失败！");
        }
        return message;
    }


}
