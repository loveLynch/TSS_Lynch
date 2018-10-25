package com.silver.tss.repository;

import com.silver.tss.domain.Topic;
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
public interface TopicRepo extends JpaRepository<Topic, Integer> {
    /**
     * 选中题目，真实数量加1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected = t.topicRealSelected +1 where t.topicId= :topicId")
    void incrementTopic(@Param("topicId") String topicId);

    /**
     * 丢弃选择题目
     * 题目真实选择减1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected = t.topicRealSelected -1 where t.topicId= :topicId")
    void DecrementTopic(@Param("topicId") String topicId);

    /**
     * 删除题目
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("delete from Topic t where t.topicId= :topicId")
    int deleteTopic(@Param("topicId") String topicId);

    /**
     * 查询topicName
     *
     * @param topicId
     */
    @Query("select t.topicName  from Topic  t  where t.topicId= :topicId")
    String getTopicName(@Param("topicId") String topicId);

    /**
     * 查询topicDescription
     *
     * @param topicId
     */
    @Query("select t.topicDescription  from Topic  t  where t.topicId= :topicId")
    String getTopicDescription(@Param("topicId") String topicId);


    /**
     * 查询最大值
     *
     * @param topicId
     */
    @Query("select t.topicMaxSelected  from Topic  t  where t.topicId= :topicId")
    String getMAXTopic(@Param("topicId") String topicId);

    /**
     * 查询真实值
     *
     * @param topicId
     */
    @Query("select t.topicRealSelected  from Topic  t  where t.topicId= :topicId")
    String getRealTopic(@Param("topicId") String topicId);


    /**
     * 更新topicName
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicName = :topicName where t.topicId= :topicId")
    int updateTopicName(@Param("topicName") String topicName, @Param("topicId") String topicId);

    /**
     * 更新topicDescription
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicDescription = :topicDescription where t.topicId= :topicId")
    int updateTopicDescription(@Param("topicDescription") String topicDescription, @Param("topicId") String topicId);


    /**
     * 更新topicMaxSelected
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicMaxSelected = :topicMaxSelected where t.topicId= :topicId")
    int updateTopicMaxSelectedo(@Param("topicMaxSelected") String topicDescription, @Param("topicId") String topicId);

    /**
     * 更新所有
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicName = :topicName,t.topicDescription = :topicDescription,t.topicMaxSelected = :topicMaxSelected where t.topicId= :topicId")
    int updateTopicAll(@Param("topicName") String topicName, @Param("topicDescription") String topicDescription, @Param("topicMaxSelected") Integer topicMaxSelected, @Param("topicId") String topicId);

    /**
     * 根据topicType查询TopicId
     *
     * @param topicType
     * @return
     */
    @Query("select t from Topic t where t.topicType = :topicType")
    List<Topic> conuttopicType(@Param("topicType") String topicType);

    /**
     * 根据TopicId查询
     *
     * @param topicId
     * @return
     */
    @Query("select t from Topic t where t.topicId = :topicId")
    List<Topic> findByTopicId(@Param("topicId") String topicId);

    /**
     * 班1选中题目，真实数量加1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected1 = t.topicRealSelected1 +1 where t.topicId= :topicId")
    void incrementTopic1(@Param("topicId") String topicId);

    /**
     * 班1丢弃选择题目
     * 题目真实选择减1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected1 = t.topicRealSelected1 -1 where t.topicId= :topicId")
    void DecrementTopic1(@Param("topicId") String topicId);

    /**
     * 班2选中题目，真实数量加1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected2 = t.topicRealSelected2 +1 where t.topicId= :topicId")
    void incrementTopic2(@Param("topicId") String topicId);

    /**
     * 班2丢弃选择题目
     * 题目真实选择减1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected2 = t.topicRealSelected2 -1 where t.topicId= :topicId")
    void DecrementTopic2(@Param("topicId") String topicId);

    /**
     * 班3选中题目，真实数量加1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected3 = t.topicRealSelected3 +1 where t.topicId= :topicId")
    void incrementTopic3(@Param("topicId") String topicId);

    /**
     * 班3丢弃选择题目
     * 题目真实选择减1
     *
     * @param topicId
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.topicRealSelected3 = t.topicRealSelected3 -1 where t.topicId= :topicId")
    void DecrementTopic3(@Param("topicId") String topicId);

    /**
     * 题目更新修改时间
     *
     * @param modifyTime
     * @param topicId
     * @return
     */
    @Modifying
    @Transactional
    @Query("update Topic t set t.modifyTime = :modifyTime where t.topicId= :topicId")
    void updateModifyTime(@Param("modifyTime") Date modifyTime, @Param("topicId") String topicId);
}
