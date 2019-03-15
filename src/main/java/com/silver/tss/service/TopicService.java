package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Student;
import com.silver.tss.domain.Topic;
import com.silver.tss.repository.StudentRepo;
import com.silver.tss.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lynch on 2018/10/21. <br>
 **/
@Service
public class TopicService {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private StudentRepo studentRepo;

    /**
     * 插入题目
     *
     * @param topic
     * @return
     */
    public int insertTopic(Topic topic) {
        try {
            topicRepo.save(topic);
        } catch (Exception e) {
        }

        return 1;
    }

    /**
     * 显示所有题目
     *
     * @return
     */
    public JSONObject findAllTopic() {
        List<Student> students = studentRepo.findAll();
        List<Topic> topics = topicRepo.findAll();
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("size", topics.size());
        response.put("list", topics);
        return response;
    }

    /**
     * 选中题目
     *
     * @param studentId
     * @param topicId
     * @return
     */
    public JSONObject selectTopic(String studentId, String topicId) {
        List<Topic> topics = topicRepo.findAll();
        String classId = null;
        Topic topic;
        if (topics.size() > 0) topic = topics.get(0);
        else return Response.response(400);
        int flag = 0;
        try {

            String topicName = topicRepo.getTopicName(topicId);
            topicRepo.incrementTopic(topicId);
            studentRepo.updateModifyTime(new Date(), studentId);
            classId = studentRepo.findClassByStudentId(studentId);
            if (classId.equals("1"))
                topicRepo.incrementTopic1(topicId);
            else if (classId.equals("2"))
                topicRepo.incrementTopic2(topicId);
            else if (classId.equals("3"))
                topicRepo.incrementTopic3(topicId);
            topicRepo.updateModifyTime(new Date(), topicId);
            flag = studentRepo.studentSelectTopic(topicId, topicName, studentId);
        } catch (Exception e) {
        }
        return flag > 0 ? Response.response(200) : Response.response(400);


    }

    /**
     * 撤销题目选择
     *
     * @param studentId
     * @param topicId
     * @return
     */
    public JSONObject dropTopic(String studentId, String topicId) {
        List<Topic> topics = topicRepo.findAll();
        String classId = null;
        Topic topic;
        if (topics.size() > 0) topic = topics.get(0);
        else return Response.response(400);
        int flag = 0;
        try {
            topicRepo.DecrementTopic(topicId);
            studentRepo.updateModifyTime(new Date(), studentId);
            classId = studentRepo.findClassByStudentId(studentId);
            if (classId.equals("1"))
                topicRepo.DecrementTopic1(topicId);
            else if (classId.equals("2"))
                topicRepo.DecrementTopic2(topicId);
            else if (classId.equals("3"))
                topicRepo.DecrementTopic3(topicId);
            topicRepo.updateModifyTime(new Date(), topicId);
            flag = studentRepo.studentDropTopic(null, null, studentId);

        } catch (Exception e) {
        }
        return flag > 0 ? Response.response(200) : Response.response(400);
    }

    /**
     * 是否超过最大值
     *
     * @param studentId
     * @param topicId
     * @return
     */
    public boolean isExceedMaxTopic(String studentId, String topicId) {
        String classId = studentRepo.findClassByStudentId(studentId);
        String real1 = topicRepo.getRealTopic1(topicId);
        String real2 = topicRepo.getRealTopic2(topicId);
        String real3 = topicRepo.getRealTopic3(topicId);
        String max = topicRepo.getMAXTopic(topicId);
        if (classId.equals("1") && Integer.parseInt(real1) >= Integer.parseInt(max))
            return true;
        else if (classId.equals("2") && Integer.parseInt(real2) >= Integer.parseInt(max))
            return true;
        else if (classId.equals("3") && Integer.parseInt(real3) >= Integer.parseInt(max))
            return true;
        else {
            return false;
        }
    }

    /**
     * 删除题目
     *
     * @param topicId
     * @return
     */
    public JSONObject deleteTopic(String topicId) {
        int flag = 0;
        try {
            flag = topicRepo.deleteTopic(topicId);

        } catch (Exception e) {
        }
        return flag > 0 ? Response.response(200) : Response.response(400);
    }

    /**
     * 更新题目
     *
     * @param topicId
     * @param data
     * @param type
     * @return
     */
    public JSONObject updateTopic(String topicId, String data, int type) {
//        200-成功; 400-失败; 401-参数错误; 402-题目人数上限小于实际选题人数
        //更新类型, 0-题目名称; 1-题目描述; 2-题目人数上限 -1-三项一起修改
        Topic topic = new Topic();
        int flag = 0;

        try {
            if (type == 0) {
                System.out.println("修改题目名称");
                flag = topicRepo.updateTopicName(data, topicId);
                topic.setModifyTime(new Date());
            } else if (type == 1) {
                System.out.println("修改题目描述");
                flag = topicRepo.updateTopicDescription(data, topicId);
                topic.setModifyTime(new Date());
            } else if (type == 2) {
                System.out.println("修改人数上限");
                flag = topicRepo.updateTopicMaxSelectedo(data, topicId);
                topic.setModifyTime(new Date());
            } else Response.response(401);
        } catch (Exception e) {
        }
        return flag > 0 ? Response.response(200) : Response.response(400);

    }

    /**
     * 根据类型统计选题人数
     *
     * @param topicType
     * @return
     */
    public JSONObject countBytopicType(String topicType) {
        List<Topic> list = null;
        try {
            list = topicRepo.conuttopicType(topicType);

        } catch (Exception e) {
        }
        return Response.response(200, list.size());
    }

    /**
     * 题目名称、描述和限选人数一起更新
     *
     * @param topic
     * @return
     */
    public JSONObject updateTopicdata(Topic topic) {

        int flag = 0;
        try {
            topicRepo.updateModifyTime(new Date(), topic.getTopicId());
            flag = topicRepo.updateTopicAll(topic.getTopicName(), topic.getTopicDescription(), topic.getTopicMaxSelected(), topic.getTopicId());

        } catch (Exception e) {
        }
        return flag > 0 ? Response.response(200) : Response.response(400);
    }

}
