package com.silver.tss.web;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.TopicTime;
import com.silver.tss.service.StatusService;
import com.silver.tss.service.TopicTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 状态管理器
 *
 * @author Yuchen Chiang
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/status")
public class StatusController {

    @Autowired
    private StatusService statusService;
    @Autowired
    private TopicTimeService topicTimeService;

    /**
     * 改变系统状态
     * /status/change/status?status=xx
     *
     * @param status 0-change pwd; 1-start select; 2-end select
     * @return {
     * "code" : 200-成功; 400-失败
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/change/status", method = RequestMethod.GET)
    public JSONObject changeStatus(int status) {
        return null;
    }

    /**
     * 设置选课时间段
     * /status/change/topictime?startdate=xx&enddate=xx
     *
     * @param startdate 开始时间
     * @param enddate   结束时间
     *                  "code" : 200-成功; 400-失败 401-时间设置不合理（开始大于结束）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/change/topictime", method = RequestMethod.GET)
    public JSONObject setTopicTime(String startdate, String enddate) {
        System.out.println(startdate + " " + enddate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starttime = null;
        Date endtime = null;
        try {
            starttime = sdf.parse(startdate);
            endtime = sdf.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return topicTimeService.insertTopicTime(starttime, endtime);
    }
}
