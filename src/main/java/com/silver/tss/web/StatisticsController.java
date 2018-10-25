package com.silver.tss.web;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.domain.Topic;
import com.silver.tss.service.StudentService;
import com.silver.tss.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 统计功能接口
 *
 * @author Yuchen Chiang
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private StudentService studentService;

    /**
     * 按题目类型统计选择人数
     * /statistics/get/type?topicType=xx
     *
     * @param topicType 题目类型
     * @return {
     * "code" : 200-成功; 400-失败
     * "count" : xxx
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/get/type", method = RequestMethod.GET)
    public JSONObject getTypeInfo(@RequestParam(value = "topicType") String topicType) {
        return topicService.countBytopicType(topicType);

    }

    /**
     * 按题目统计选择人数
     * /statistics/get/topic?topicId=xx
     *
     * @param topicId 题目ID
     * @return {
     * "code" : 200-成功; 400-失败
     * "count" : xxx
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/get/topic", method = RequestMethod.GET)
    public JSONObject getTopicInfo(@RequestParam(value = "topicId") String topicId) {
        return studentService.countBytopicId(topicId);
    }

    /**
     * 按班级统计选择人数
     * /statistics/get/class?classId=xx
     *
     * @param classId 班级ID
     * @return {
     * "code" : 200-成功; 400-失败
     * "count" : xxx
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/get/class", method = RequestMethod.GET)
    public JSONObject getClassInfo(@RequestParam(value = "classId") String classId) {
        return studentService.countByclassId(classId);
    }

    /**
     * 统计全局选题人数
     * /statistics/get/final
     *
     * @return {
     * "code" : 200-成功; 400-失败
     * "count" : xxx
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/get/final", method = RequestMethod.GET)
    public JSONObject getFinalInfo() {
        return studentService.countByAll();

    }
}
