package com.silver.tss.repository;

import com.silver.tss.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lynch on 2018/10/21. <br>
 **/
@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {


    /**
     * 学生选择题目
     *
     * @param topicId
     * @param topicName
     * @param studentId
     */
    @Modifying
    @Transactional
    @Query("update Student s set  s.topicId = :topicId ,s.topicName = :topicName  where s.studentId= :studentId")
    int studentSelectTopic(@Param("topicId") String topicId, @Param("topicName") String topicName, @Param("studentId") String studentId);


    /**
     * 学生丢弃题目
     *
     * @param topicId
     * @param topicName
     * @param studentId
     */
    @Modifying
    @Transactional
    @Query("update Student s set s.topicId = :topicId , s.topicName = :topicName  where s.studentId= :studentId")
    int studentDropTopic(@Param("topicId") String topicId, @Param("topicName") String topicName, @Param("studentId") String studentId);


    /**
     * 查询学生题目
     *
     * @param studentId
     * @return
     */
    @Query("select t.topicId  from Student  t  where t.studentId= :studentId")
    String getstudentTopic(@Param("studentId") String studentId);

    /**
     * 由班级统计学生
     *
     * @param classId
     * @return
     */
    @Query("select t from Student t where t.classId= :classId")
    List<Student> findByClassId(@Param("classId") String classId);

    /**
     * 查询ClassId
     *
     * @param studentId
     * @return
     */
    @Query("select t.classId from Student t where t.studentId= :studentId")
    String findClassByStudentId(@Param("studentId") String studentId);

    /**
     * 查询studentName
     *
     * @param studentId
     * @return
     */
    @Query("select t.studentName from Student t where t.studentId= :studentId")
    String findNameByStudentId(@Param("studentId") String studentId);

    /**
     * 选题与取消题目修改时间
     *
     * @param modifyTime
     * @param studentId
     * @return
     */
    @Modifying
    @Transactional
    @Query("update Student s set s.modifyTime = :modifyTime where s.studentId= :studentId")
    void updateModifyTime(@Param("modifyTime") Date modifyTime, @Param("studentId") String studentId);
}
