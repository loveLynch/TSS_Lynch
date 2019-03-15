package com.silver.tss.repository;

import com.silver.tss.domain.TopicTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by lynch on 2018/10/30. <br>
 **/
@Repository
public interface TopicTimeRepo extends JpaRepository<TopicTime, Integer> {
    /**
     * 查询开始时间
     *
     * @param
     */
    @Query("select t.startDate  from TopicTime  t")
    Date queryStartTime();

    /**
     * 查询结束时间
     *
     * @param
     */
    @Query("select t.endDate  from TopicTime  t  ")
    Date QueryEndTime();

    /**
     * 更新选课时间
     *
     * @param startDate
     * @param endDate
     */
    @Modifying
    @Transactional
    @Query("update  TopicTime  t set t.startDate = :startDate,t.endDate= :endDate")
    int updateTopicTime(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


}
