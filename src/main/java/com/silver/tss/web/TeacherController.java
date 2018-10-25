package com.silver.tss.web;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 老师账户管理接口
 * Created by lynch on 2018/10/24. <br>
 **/
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 老师账户登录
     * /teacher/login?teacherId=xx&teacherPwd=xx
     *
     * @param teacherId  学号
     * @param teacherPwd 密码
     * @return {
     * "code" : 200-成功; 300-需更新密码; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JSONObject login(@RequestParam(value = "teacherId") String teacherId, @RequestParam(value = "teacherPwd") String teacherPwd) {
        JSONObject response = teacherService.isTeacherExist(teacherId, teacherPwd);
        return "200".equals(response.getString("code")) ?
                teacherService.isTeacherChangePwd(teacherId) ? response : Response.response(300)
                : Response.response(400);
    }

    /**
     * 老师账户密码更改
     * /teacher/update/pwd?teacherId=xx&teacherPwd=xx
     *
     * @param teacherId  学号
     * @param teacherPwd 密码
     * @return {
     * "code" : 200-成功; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/update/pwd", method = RequestMethod.GET)
    public JSONObject updatePwd(@RequestParam(value = "teacherId") String teacherId, @RequestParam(value = "teacherPwd") String teacherPwd) {
        return teacherService.updateteacherPwd(teacherId, teacherPwd);
    }


}
