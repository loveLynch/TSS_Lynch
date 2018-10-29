package com.silver.tss.web;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 学生账户管理接口
 *
 * @author Yuchen Chiang
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/student")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private UserService userService;

    /**
     * 学生账户登录
     * /student/login?studentId=xx&studentPwd=xx
     *
     * @param studentId  学号
     * @param studentPwd 密码
     * @return {
     * "code" : 200-成功; 300-需更新密码; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JSONObject login(@RequestParam(value = "studentId") String studentId, @RequestParam(value = "studentPwd") String studentPwd) {
        LOGGER.info("studentId={} with studentPwd={} login tss", studentId, studentPwd);
        JSONObject response = userService.isUserExist(studentId, studentPwd);
        return "200".equals(response.getString("code")) ?
                userService.isUserChangePwd(studentId) ? response : Response.response(300)
                : Response.response(400);
    }

    /**
     * 学生账户密码更改
     * /student/update/pwd?studentId=xx&studentPwd=xx
     *
     * @param studentId  学号
     * @param studentPwd 密码
     * @return {
     * "code" : 200-成功; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/update/pwd", method = RequestMethod.GET)
    public JSONObject updatePwd(@RequestParam(value = "studentId") String studentId, @RequestParam(value = "studentPwd") String studentPwd) {
        LOGGER.info("studentId={} is trying to change tss pwd={}", studentId, studentPwd);
        return userService.updateStudentPwd(studentId, studentPwd);
    }

    /**
     * 按班级获取学生账户列表
     * /student/get/list?classId=xx
     *
     * @param classId 班级号 1-1班; 2-2班; 3-3班; -1-全部班级
     * @return {
     * "code" : 200-成功; 400-失败
     * "size" : 1
     * "list" : [
     * {
     * "id" : "xxx",
     * "studentId" : "xxx",
     * "studentName" : "xxx",
     * "classId" : "xxx",
     * "topicId" : "xxx",
     * "topicName" : "xxx",
     * "yn" : "true",
     * "createTime" : "xxx",
     * "modifiedTime" : "xxx"
     * }
     * ]
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/get/list", method = RequestMethod.GET)
    public JSONObject getStudentsList(@RequestParam(value = "classId") String classId) {
        LOGGER.info("query student info list with classId={}", classId);
        return userService.findStudentByClassId(classId);
    }

}
