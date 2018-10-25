package com.silver.tss.repository;

import com.silver.tss.domain.User;
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
public interface UserRepo extends JpaRepository<User, Integer> {
    /**
     * 查找学号和密码
     *
     * @param studentId
     * @param studentPwd
     * @return
     */
    @Query("select t from User t where t.studentId = :studentId and t.studentPwd = :studentPwd")
    List<User> getStuNameandPwd(@Param("studentId") String studentId, @Param("studentPwd") String studentPwd);

    /**
     * 查询学生密码
     *
     * @param studentId
     * @return
     */
    @Query("select t.studentPwd  from User  t  where t.studentId= :studentId")
    String getStudentPwd(@Param("studentId") String studentId);

    /**
     * 修改学生密码
     *
     * @param studentId
     * @param studentPwd
     * @return
     */
    @Modifying
    @Transactional
    @Query("update User s set s.studentPwd = :studentPwd where s.studentId= :studentId")
    int updateStudentPwd(@Param("studentId") String studentId, @Param("studentPwd") String studentPwd);

    /**
     * 改密修改时间
     *
     * @param modifyTime
     * @param studentId
     * @return
     */
    @Modifying
    @Transactional
    @Query("update User s set s.modifyTime = :modifyTime where s.studentId= :studentId")
    void updateModifyTime(@Param("modifyTime") Date modifyTime, @Param("studentId") String studentId);

}
