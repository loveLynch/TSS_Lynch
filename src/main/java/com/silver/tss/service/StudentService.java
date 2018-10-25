package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Student;
import com.silver.tss.domain.Topic;
import com.silver.tss.domain.User;
import com.silver.tss.repository.StudentRepo;
import com.silver.tss.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by lynch on 2018/10/21. <br>
 **/
@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TopicRepo topicRepo;

    public List<Student> findAllStudent() {
        List<Student> list = null;
        try {
            list = studentRepo.findAll();
        } catch (Exception e) {
        }
        return list;

    }


    /**
     * 根据题目统计选题人数
     *
     * @param topicId
     * @return
     */
    public JSONObject countBytopicId(String topicId) {
        List<Student> list = null;
        int count = 0;
        try {
            list = studentRepo.findAll();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTopicId().equals(topicId))
                    count++;
            }
        } catch (Exception e) {
        }
        return Response.response(200, count);
    }

    /**
     * 根据班级统计选题人数
     *
     * @param classId
     * @return
     */
    public JSONObject countByclassId(String classId) {
        List<Student> list = null;
        int count = 0;
        try {
            list = studentRepo.findAll();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClassId().equals(classId) && !list.get(i).getTopicId().equals("null"))
                    count++;
            }
        } catch (Exception e) {
        }
        return Response.response(200, count);

    }


    /**
     * 全局统计选题人数
     *
     * @return
     */
    public JSONObject countByAll() {
        List<Student> list = null;
        int count = 0;
        try {
            list = studentRepo.findAll();
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getTopicId().equals("null"))
                    count++;
            }
        } catch (Exception e) {
        }
        return Response.response(200, count);

    }


    /**
     * 学生是否选题
     *
     * @param studentId
     * @return
     */
    public Boolean isStudentUserHasTopic(String studentId) {
        boolean flag = false;
        try {
            String topicId = studentRepo.getstudentTopic(studentId);
            if (!topicId.equals("null"))
                flag = true;
            else flag = false;
        } catch (Exception e) {
        }
        return flag;

    }

    /**
     * 由班级统计学生
     *
     * @param classId
     * @return
     */
    public List<Student> findStudentByClassId(String classId) {
        List<Student> list = null;
        try {
            if (classId.equals("-1"))
                list = studentRepo.findAll();
            else
                list = studentRepo.findByClassId(classId);
        } catch (Exception e) {
        }
        return list;


    }

    /**
     * 导入学生数据
     *
     * @return
     */
    public int insertStudent(Student student) {
        try {
            studentRepo.save(student);
        } catch (Exception e) {
        }
        return 1;

    }


    /**
     * 显示学生已选题目
     *
     * @param studentId
     * @return
     */
    public JSONObject showSelected(String studentId) {
        List<Topic> topics = new ArrayList<>();
        String topicId = studentRepo.getstudentTopic(studentId);
        topics = topicRepo.findByTopicId(topicId);
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("size", topics.size());
        if (topics.size() > 0)
            response.put("list", topics);
        else response.put("list", "{[]}");
        return response;
    }


}
