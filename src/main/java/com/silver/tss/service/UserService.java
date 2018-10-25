package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Student;
import com.silver.tss.domain.User;
import com.silver.tss.repository.StudentRepo;
import com.silver.tss.repository.UserRepo;
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
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StudentRepo studentRepo;

    /**
     * 修改学生登陆密码
     *
     * @param studentId
     * @param studentPwd
     * @return
     */
    public JSONObject updateStudentPwd(String studentId, String studentPwd) {
        int flag = 0;
        try {
            flag = userRepo.updateStudentPwd(studentId, studentPwd);
            userRepo.updateModifyTime(new Date(), studentId);
        } catch (Exception e) {

        }
        return flag > 0 ? Response.response(200) : Response.response(400);
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
        try {
            list = userRepo.getStuNameandPwd(studentId, studentPwd);
            classId = studentRepo.findClassByStudentId(studentId);
            studentName = studentRepo.findNameByStudentId(studentId);


        } catch (Exception e) {

        }
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("classId", classId);
        response.put("studentId", studentId);
        response.put("studentName", studentName);
        if (list.size() > 0)
            return response;
        else return Response.response(400);


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
