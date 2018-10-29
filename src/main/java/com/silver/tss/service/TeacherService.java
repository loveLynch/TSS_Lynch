package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Teacher;
import com.silver.tss.domain.User;
import com.silver.tss.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lynch on 2018/10/21. <br>
 **/
@Service
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    public int insertTeacher(Teacher teacher) {
        try {
            teacherRepo.save(teacher);
        } catch (Exception e) {
        }

        return 1;
    }

    /**
     * 老师登陆
     * 账户是否存在
     *
     * @param teacherId
     * @param teacherPwd
     * @return
     */
    public JSONObject isTeacherExist(String teacherId, String teacherPwd) {
        List<Teacher> list = null;
        String teacherName = null;
        try {
            list = teacherRepo.getTeaNameandPwd(teacherId, teacherPwd);
            teacherName = teacherRepo.findNameByTeacherId(teacherId);


        } catch (Exception e) {

        }
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("teacherId", teacherId);
        response.put("teacherName", teacherName);
        if (list.size() > 0)
            return response;
        else return Response.response(400);


    }

    /**
     * 老师是否修改密码
     *
     * @param teacherId
     * @return
     */
    public boolean isTeacherChangePwd(String teacherId) {
        String studentPwd = teacherRepo.getTeacherPwd(teacherId);

        if (!studentPwd.equals("666666"))
            return true;
        else
            return false;
    }

    /**
     * 老师权限获取
     *
     * @param teacherId
     * @return
     */
    public boolean isTeacherIsAuthority(String teacherId) {
        String authority = teacherRepo.getTeacherAuthority(teacherId);
        if (authority.equals("admin"))
            return true;
        else
            return false;
    }

    /**
     * 老师密码修改
     *
     * @param teacherId
     * @param teacherPwd
     * @return
     */
    public JSONObject updateteacherPwd(String teacherId, String teacherPwd) {
        int flag = 0;
        try {
            flag = teacherRepo.updateTeacherPwd(teacherId, teacherPwd);
            teacherRepo.updateModifyTime(new Date(), teacherId);
        } catch (Exception e) {

        }
        return flag > 0 ? Response.response(200) : Response.response(400);
    }


}
