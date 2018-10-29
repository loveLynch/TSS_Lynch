package com.silver.tss.web;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.service.ExcelService;
import com.silver.tss.service.TeacherService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


/**
 * EXCEL导入导出接口
 *
 * @author Yuchen Chiang
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 导入学生EXCEL
     * [POST] /excel/import/students
     *
     * @param file excel
     * @return {
     * "code" : 200-成功; 400-失败 402-没有权限
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/import/students", method = RequestMethod.POST)
    public JSONObject importStudents(@RequestParam(value = "file") MultipartFile file) {
        if (!teacherService.isTeacherIsAuthority(TeacherController.teacherIdtoAuthority)) {
            System.out.println("您没有权限！");
            return Response.response(402);
        } else
            return file.getSize() <= 0 ? Response.response(400) : excelService.importStudentsExcel(file.getOriginalFilename(), file);

    }

    /**
     * 导入题目EXCEL
     * [POST] /excel/import/topics
     *
     * @param file excel
     * @return {
     * "code" : 200-成功; 400-失败 402-没有权限
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/import/topics", method = RequestMethod.POST)
    public JSONObject importTopics(@RequestParam(value = "file") MultipartFile file) {
        if (!teacherService.isTeacherIsAuthority(TeacherController.teacherIdtoAuthority)) {
            System.out.println("您没有权限！");
            return Response.response(402);
        } else
            return file.getSize() <= 0 ? Response.response(400) : excelService.importTopicsExcel(file.getOriginalFilename(), file);


    }

    /**
     * 导入题目word or picture等
     * [POST] /excel/import/osstopics
     *
     * @param file word or picture
     * @return {
     * "code" : 200-成功; 400-失败 401-文件名设置不合格 -402没有权限
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/import/osstopics", method = RequestMethod.POST)
    public JSONObject importOSSTopics(@RequestParam(value = "file") MultipartFile file) {
        if (!teacherService.isTeacherIsAuthority(TeacherController.teacherIdtoAuthority)) {
            System.out.println("您没有权限！");
            return Response.response(402);
        } else {
            if (!excelService.isTopicFormat(file))
                return Response.response(401);
            else
                return excelService.importTopicstoOSS(file.getOriginalFilename(), file);
        }


    }

    /**
     * 导入教师EXCEL
     * [POST] /excel/import/teachers
     *
     * @param file excel
     * @return {
     * "code" : 200-成功; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/import/teachers", method = RequestMethod.POST)
    public JSONObject importTeachers(@RequestParam(value = "file") MultipartFile file) {
        return file.getSize() <= 0 ? Response.response(400) : excelService.importTeachersExcel(file.getOriginalFilename(), file);

    }

    /**
     * 导出学生选题EXCEL
     * [GET] /excel/export/students?classId=1
     *
     * @param classId 班级号; classId=-1 导出全部选题信息表
     * @return excel
     */
    @ResponseBody
    @RequestMapping(value = "/export/students", method = RequestMethod.GET)
    public void exportStudents(String classId, HttpServletResponse response) {
        Workbook wb = excelService.exportStudentsExcel(classId);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=export_" + classId + ".xls");
        try {
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException ioe) {
        }
    }
}
