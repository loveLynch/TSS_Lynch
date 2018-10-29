package com.silver.tss.web;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Topic;
import com.silver.tss.service.StatusService;
import com.silver.tss.service.StudentService;
import com.silver.tss.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课程设计题目管理接口
 *
 * @author Yuchen Chiang
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StatusService statusService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);

    /**
     * 学生选中题目
     * /topic/select/topic?studentId=xx&topicId=xx
     *
     * @param studentId 学生ID
     * @param topicId   题目ID
     * @return {
     * "code" : 200-成功; 400-失败; 401-学生已选过该题; 402-选题人数超上限 -403不在选课时间段
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/select/topic", method = RequestMethod.GET)
    public JSONObject selectTopic(String studentId, String topicId) {
        if (statusService.isStatus0() || statusService.isStatus2()) {
            LOGGER.info("studentId={} select topicId={} failed, cause = 403", studentId, topicId);
            return Response.response(403);
        } else if (studentService.isStudentUserHasTopic(studentId)) {
            LOGGER.info("studentId={} select topicId={} failed, cause = 401", studentId, topicId);
            return Response.response(401);
        } else {
            if (topicService.isExceedMaxTopic(studentId, topicId)) {
                LOGGER.info("studentId={} select topicId={} in classId={} failed, cause code = 402", studentId, topicId);
                return Response.response(402);
            } else {
                LOGGER.info("studentId={} select topicId={} success", studentId, topicId);
                return topicService.selectTopic(studentId, topicId);
            }

        }

    }

    /**
     * 学生丢弃已选题目
     * /topic/drop/topic?studentId=xx&topicId=xx
     *
     * @param studentId 学生ID
     * @param topicId   题目ID
     * @return {
     * "code" : 200-成功; 400-失败; 401-该学生未选择本题目  -402不在选课时间段
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/drop/topic", method = RequestMethod.GET)
    public JSONObject dropTopic(String studentId, String topicId) {
        if (statusService.isStatus0() || statusService.isStatus2()) {
            LOGGER.info("studentId={} drop topicId={} failed, cause = 402", studentId, topicId);
            return Response.response(402);
        } else if (!studentService.isStudentUserHasTopic(studentId)) {
            LOGGER.info("studentId={} drop topicId={} failed, cause code = 401", studentId, topicId);
            return Response.response(401);
        } else {
            LOGGER.info("studentId={} drop topicId={} success", studentId, topicId);
            return topicService.dropTopic(studentId, topicId);
        }
    }

    /**
     * 学生已选题目显示
     * /topic/show/selectedtopic?studentId=xx
     *
     * @param studentId
     * @return "code":200-成功； 400-失败; 401-该学生未选择题目
     */
    @ResponseBody
    @RequestMapping(value = "/show/selectedtopic", method = RequestMethod.GET)
    public JSONObject selectedTopic(String studentId) {
        if (studentService.isStudentUserHasTopic(studentId)) {
            LOGGER.info("show studentId={} selected topic success", studentId);
            return studentService.showSelected(studentId);
        } else {
            LOGGER.info("studentId={} show failed without topic, cause code = 401", studentId);
            return Response.response(401);
        }
    }


    /**
     * 删除题目
     * /topic/delete/topic?topicId=xx
     *
     * @param topicId 题目ID
     * @return {
     * "code" : 200-成功; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/delete/topic", method = RequestMethod.GET)
    public JSONObject deleteTopic(String topicId) {
        return topicService.deleteTopic(topicId);
    }

    /**
     * 更新题目
     * /topic/update/topic?topicId=xx&data=xx&type=xx
     *
     * @param topicId 题目ID
     * @param data    待更新数据
     * @param type    更新类型, 0-题目名称; 1-题目描述; 2-题目人数上限
     * @return {
     * "code" : 200-成功; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/update/topic", method = RequestMethod.GET)
    public JSONObject updateTopic(String topicId, String data, String type) {
        return topicService.updateTopic(topicId, data, Integer.parseInt(type));
    }

    /**
     * 更新题目
     * /topic/update/data
     */
    @ResponseBody
    @RequestMapping(value = "/update/data", method = RequestMethod.POST)
    public JSONObject updateTopicData(@RequestBody Topic topic) {
        Topic gettopic = topic;
        return topicService.updateTopicdata(gettopic);

    }


    /**
     * 获取题目列表
     * /topic/get/list
     *
     * @return {
     * "code" : 200-成功; 400-失败
     * "size" : 1
     * "list" : [
     * {
     * "id" : "xxx",
     * "topicId" : "xxx",
     * "topicName" : "xxx",
     * "topicType" : "xxx",
     * "topicDescription" : "xxx",
     * "topicMaxSelected" : "xxx",
     * "topicRealSelected" : "xxx",
     * "yn" : "true",
     * "createTime" : "xxx",
     * "modifiedTime" : "xxx"
     * }
     * ]
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/get/list", method = RequestMethod.GET)
    public JSONObject getTopicsList() {
        LOGGER.info("query topic info list");
        return topicService.findAllTopic();

    }
}
