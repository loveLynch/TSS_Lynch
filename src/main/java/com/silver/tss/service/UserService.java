package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Student;
import com.silver.tss.domain.User;
import com.silver.tss.repository.StudentRepo;
import com.silver.tss.repository.TopicRepo;
import com.silver.tss.repository.UserRepo;
import com.silver.tss.web.StudentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lynch on 2018/10/21. <br>
 **/
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TopicRepo topicRepo;

    /**
     * 修改学生登陆密码
     *
     * @param studentId
     * @param studentPwd
     * @return
     */
    public JSONObject updateStudentPwd(String studentId, String studentPwd) {
        int flag = 0;
        String classId = null;
        String studentName = null;
        String topicName = null;
        String topicId = null;
        try {
            flag = userRepo.updateStudentPwd(studentId, studentPwd);
            userRepo.updateModifyTime(new Date(), studentId);
            classId = studentRepo.findClassByStudentId(studentId);
            studentName = studentRepo.findNameByStudentId(studentId);
            topicId = studentRepo.getstudentTopic(studentId);
            topicName = topicRepo.getTopicName(topicId);
        } catch (Exception e) {

        }
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("classId", classId);
        response.put("studentId", studentId);
        response.put("studentName", studentName);
        response.put("topicName", topicName);
        response.put("topicId", topicId);
        return flag > 0 ? response : Response.response(400);
    }

    /**
     * 用户是否改密
     *
     * @param studentId
     * @return
     */
    public Boolean isUserChangePwd(String studentId) {
        String studentPwd = userRepo.getStudentPwd(studentId);

        if (!studentPwd.equals("666666"))
            return true;
        else
            return false;


    }

    /**
     * 用户登录
     * 用户是否存在
     *
     * @param studentId
     * @param studentPwd
     * @return
     */
    public JSONObject isUserExist(String studentId, String studentPwd) {
        List<User> list = null;
        String classId = null;
        String studentName = null;
        String topicName = null;
        String topicId = null;
        try {
            list = userRepo.getStuNameandPwd(studentId, studentPwd);
            classId = studentRepo.findClassByStudentId(studentId);
            studentName = studentRepo.findNameByStudentId(studentId);
            topicId = studentRepo.getstudentTopic(studentId);
            topicName = topicRepo.getTopicName(topicId);

        } catch (Exception e) {

        }
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("classId", classId);
        response.put("studentId", studentId);
        response.put("studentName", studentName);
        response.put("topicName", topicName);
        response.put("topicId", topicId);
        if (list.size() > 0) {
            LOGGER.info("studentId={} with studentPwd={} login tss success", studentId, studentPwd);
            return response;
        } else {
            LOGGER.info("studentId={} with studentPwd={} login tss fail", studentId, studentPwd);
            return Response.response(400);
        }


    }

    /**
     * 插入学生
     *
     * @param user
     * @return
     */
    public int insertUser(User user) {
        try {
            userRepo.save(user);
        } catch (Exception e) {
        }

        return 1;
    }

    /**
     * 由班级统计学生
     *
     * @param classId
     * @return
     */
    public JSONObject findStudentByClassId(String classId) {
        List<Student> list = null;
        try {
            if (classId.equals("-1"))
                list = studentRepo.findAll();
            else
                list = studentRepo.findByClassId(classId);
        } catch (Exception e) {
        }
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("size", list.size());
        response.put("list", list);
        return response;


    }


}
