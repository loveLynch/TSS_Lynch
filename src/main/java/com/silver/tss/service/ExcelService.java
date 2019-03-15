package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.silver.tss.common.Response;
import com.silver.tss.domain.*;
import com.silver.tss.repository.TopicRepo;
import com.silver.tss.utils.AliyunOSSConfig;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private TopicTimeService topicTimeService;

    /**
     * 导入学生数据
     *
     * @param fileName
     * @param file
     * @return
     */
    public JSONObject importStudentsExcel(String fileName, MultipartFile file) {
        topicTimeService.initTime(new Date(), new Date());

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

    /**
     * 导入老师数据
     *
     * @param fileName
     * @param file
     * @return
     */
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

    /**
     * 导入题目信息
     *
     * @param fileName
     * @param file
     * @return
     */
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

    /**
     * 导出选题信息
     *
     * @param classId
     * @return
     */
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
        row0.createCell(3).setCellValue("类型");
        row0.createCell(4).setCellValue("题目");

        int r = 1;
        for (Student student : students) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(student.getStudentId());
            row.createCell(1).setCellValue(student.getStudentName());
            row.createCell(2).setCellValue(student.getClassId());
            row.createCell(3).setCellValue(topicRepo.findtopicType(student.getTopicId()));
            row.createCell(4).setCellValue(topicRepo.getTopicName(student.getTopicId()));
        }

        return wb;
    }

    /**
     * 将word或picture等传到Oss
     *
     * @param filename
     * @param file
     * @return
     */
    public JSONObject importTopicstoOSS(String filename, MultipartFile file) {
        if (file == null || file.getSize() <= 0)
            Response.response(400);
        String finalURL = null;
        int topicConut = 0;
        if (file != null) {
            try {
//            // 获取文件后缀名
//            String suffix = getSuffix(file);
                // 填自己的帐号信息
                String endpoint = AliyunOSSConfig.ALIYUNOSS_END_POINT;
                String accessKeyId = AliyunOSSConfig.ALIYUNOSS_ACCESS_KEY_ID;
                String accessKeySecret = AliyunOSSConfig.ALIYUNOSS_ACCESS_KEY_SECRET;
                // 创建OSSClient实例
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                // 文件桶
                String bucketName = AliyunOSSConfig.ALIYUNOSS_BUCKET_NAME;
                // 上传文件
                ossClient.putObject(bucketName, filename, new ByteArrayInputStream(file.getBytes()));
                // 设置URL过期时间为1年，默认这里是int型，转换为long型即可
                Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365);
                // 生成URL
                URL url = ossClient.generatePresignedUrl(bucketName, filename, expiration);
                finalURL = url.toString().split("\\?")[0];
                String fileNostuffix = filename.substring(0, filename.lastIndexOf("."));
                String trimfilename[] = fileNostuffix.split("_");
                String topicId = trimfilename[0];
                String topicType = trimfilename[1];
                String topicName = trimfilename[2];
                String topicMaxSelected = trimfilename[3];
                Topic topic = new Topic();
                topic.setTopicId(topicId);
                topic.setTopicType(topicType);
                topic.setTopicName(topicName);
                topic.setTopicDescription(finalURL);
                topic.setTopicMaxSelected(Integer.valueOf(topicMaxSelected));
                topic.setTopicRealSelected(0);
                topic.setTopicRealSelected1(0);
                topic.setTopicRealSelected2(0);
                topic.setTopicRealSelected3(0);
                topic.setYn(true);
                topicConut += topicService.insertTopic(topic);
            } catch (Exception e) {

            }
        }
        System.out.println(finalURL);
        return Response.response(200);
    }


    /**
     * 判断excel后缀
     *
     * @param fileName
     * @param file
     * @return
     */
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

    /**
     * 获取文 MultipartFile 文件名
     * 并进行解析判断
     *
     * @param file
     * @return
     */
    public boolean isTopicFormat(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileNostuffix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String trimfilename[] = fileNostuffix.split("_");
        Pattern pattern = Pattern.compile("[0-9]*");
        if (trimfilename.length == 4 && pattern.matcher(trimfilename[0]).matches() && pattern.matcher(trimfilename[3]).matches())
            return true;
        else
            return false;
    }
}
