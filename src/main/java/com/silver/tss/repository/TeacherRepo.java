package com.silver.tss.repository;

import com.silver.tss.domain.Teacher;
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
public interface TeacherRepo extends JpaRepository<Teacher, Integer> {

    /**
     * 获取老师账户和密码
     *
     * @param teacherId
     * @param teacherPwd
     * @return
     */
    @Query("select t from Teacher t where t.teacherId = :teacherId and t.teacherPwd = :teacherPwd")
    List<Teacher> getTeaNameandPwd(@Param("teacherId") String teacherId, @Param("teacherPwd") String teacherPwd);

    /**
     * 获取名字
     *
     * @param teacherId
     * @return
     */
    @Query("select t.teacherName from Teacher t where t.teacherId= :teacherId")
    String findNameByTeacherId(@Param("teacherId") String teacherId);


    /**
     * 获取密码
     *
     * @param teacherId
     * @return
     */
    @Query("select t.teacherPwd  from  Teacher t  where t.teacherId= :teacherId")
    String getTeacherPwd(@Param("teacherId") String teacherId);

    /**
     * 获取权限
     *
     * @param teacherId
     * @return
     */
    @Query("select t.authority  from  Teacher t  where t.teacherId= :teacherId")
    String getTeacherAuthority(@Param("teacherId") String teacherId);

    /**
     * 更新账户和密码
     *
     * @param teacherId
     * @param teacherPwd
     * @return
     */
    @Modifying
    @Transactional
    @Query("update Teacher s set s.teacherPwd = :teacherPwd where s.teacherId= :teacherId")
    int updateTeacherPwd(@Param("teacherId") String teacherId, @Param("teacherPwd") String teacherPwd);

    /**
     * 改密修改时间
     *
     * @param modifyTime
     * @param teacherId
     * @return
     */
    @Modifying
    @Transactional
    @Query("update Teacher s set s.modifyTime = :modifyTime where s.teacherId= :teacherId")
    void updateModifyTime(@Param("modifyTime") Date modifyTime, @Param("teacherId") String teacherId);


}
