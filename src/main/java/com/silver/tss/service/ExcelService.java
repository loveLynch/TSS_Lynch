package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Student;
import com.silver.tss.domain.Teacher;
import com.silver.tss.domain.Topic;
import com.silver.tss.domain.User;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by lynch on 2018/10/21. <br>
 **/
@Service
public class ExcelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TopicService topicService;

    public JSONObject importStudentsExcel(String fileName, MultipartFile file) {

//        String classId = fileName.substring(0, fileName.lastIndexOf("."));
        Workbook wb = getReadWorkBook(fileName, file);
        if (wb == null) return Response.response(400);
        int studentCount = 0;
        int userConut = 0;
        Sheet sheet = wb.getSheetAt(0);
        System.out.println(sheet);
        if (sheet != null) {
            for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String classId = row.getCell(1).getStringCellValue();
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String studentId = row.getCell(2).getStringCellValue();
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                String studentName = row.getCell(3).getStringCellValue();

                Student student = new Student();
                student.setStudentId(studentId);
                student.setStudentName(studentName);
                student.setClassId(classId);
                student.setTopicId("null");
                student.setTopicName("null");
                student.setYn(true);


                User user = new User();
                user.setStudentId(studentId);
                user.setStudentPwd("666666");
                user.setStudentStatus(false);
//                user.setYn(true);


                studentCount += studentService.insertStudent(student);
                userConut += userService.insertUser(user);
            }
        }

        try {
            wb.close();
        } catch (Exception e) {
        }
        return Response.response(200);
    }

    public JSONObject importTeachersExcel(String fileName, MultipartFile file) {
        Workbook wb = getReadWorkBook(fileName, file);
        if (wb == null) return Response.response(400);
        int teacherConut = 0;
        Sheet sheet = wb.getSheetAt(0);
        System.out.println(sheet);
        if (sheet != null) {
            for (int r = 1; r <= sheet.getPhysicalNumberOfRows(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                String classId = row.getCell(0).getStringCellValue();
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String teacherId = row.getCell(1).getStringCellValue();
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String teacherName = row.getCell(2).getStringCellValue();
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                String authority = row.getCell(3).getStringCellValue();

                Teacher teacher = new Teacher();
                teacher.setClassId(classId);
                teacher.setTeacherId(teacherId);
                teacher.setTeacherName(teacherName);
                teacher.setTeacherPwd("666666");
                teacher.setAuthority(authority);
                teacher.setYn(true);


                teacherConut += teacherService.insertTeacher(teacher);
            }
        }

        try {
            wb.close();
        } catch (Exception e) {
            LOGGER.info("" + e);
        }
        return Response.response(200);
    }

    public JSONObject importTopicsExcel(String fileName, MultipartFile file) {

        Workbook wb = getReadWorkBook(fileName, file);
        if (wb == null) return Response.response(400);
        int topicConut = 0;
        Sheet sheet = wb.getSheetAt(0);
        System.out.println(sheet);
        if (sheet != null) {
            for (int r = 1; r <= sheet.getPhysicalNumberOfRows(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                String topicId = row.getCell(0).getStringCellValue();
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String topicType = row.getCell(1).getStringCellValue();
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String topicName = row.getCell(2).getStringCellValue();
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                String topicDescription = row.getCell(3).getStringCellValue();
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                String topicMaxSelected = row.getCell(4).getStringCellValue();

                Topic topic = new Topic();
                topic.setTopicId(topicId);
                topic.setTopicType(topicType);
                topic.setTopicName(topicName);
                topic.setTopicDescription(topicDescription);
                topic.setTopicMaxSelected(Integer.valueOf(topicMaxSelected));
                topic.setTopicRealSelected(0);
                topic.setTopicRealSelected1(0);
                topic.setTopicRealSelected2(0);
                topic.setTopicRealSelected3(0);
                topic.setYn(true);


                topicConut += topicService.insertTopic(topic);
            }
        }

        try {
            wb.close();
        } catch (Exception e) {
            LOGGER.info("" + e);
        }

        return Response.response(200);
    }

    public Workbook exportStudentsExcel(String classId) {
        List<Student> students = null;
        try {
            if (classId.equals("-1")) studentService.findAllStudent();
            students = studentService.findStudentByClassId(classId);
        } catch (Exception e) {

        }
        Workbook wb = new HSSFWorkbook();
        HSSFSheet sheet = ((HSSFWorkbook) wb).createSheet();
        HSSFRow row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("学号");
        row0.createCell(1).setCellValue("姓名");
        row0.createCell(2).setCellValue("班号");
        row0.createCell(3).setCellValue("题目");

        int r = 1;
        for (Student student : students) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(student.getStudentId());
            row.createCell(1).setCellValue(student.getStudentName());
            row.createCell(2).setCellValue(student.getClassId());
            row.createCell(3).setCellValue(student.getTopicName());
        }

        return wb;
    }


    private Workbook getReadWorkBook(String fileName, MultipartFile file) {
        Workbook wb;
        try {
            if (fileName.matches("^.+\\.(?i)(xls)$")) {
                wb = new HSSFWorkbook(file.getInputStream());
            } else if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                wb = new XSSFWorkbook(file.getInputStream());
            } else {
                LOGGER.error("文件({})上传格式错误", fileName);
                return null;
            }
        } catch (Exception e) {
            LOGGER.info("" + e);
            return null;
        }
        return wb;
    }
}
