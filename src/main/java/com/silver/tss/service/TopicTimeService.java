package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.TopicTime;
import com.silver.tss.repository.TopicTimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lynch on 2018/10/30. <br>
 **/
@Service
public class TopicTimeService {
    @Autowired
    private TopicTimeRepo topicTimeRepo;

    /**
     * 老师修改选课时间
     *
     * @param startdate
     * @param enddate
     * @return
     */
    public JSONObject insertTopicTime(Date startdate, Date enddate) {
        if (startdate == null) return Response.response(400);
        if (enddate == null) return Response.response(400);
        int flag = 0;
        try {
            flag = topicTimeRepo.updateTopicTime(startdate, enddate);
        } catch (Exception e) {
        }
        return flag > 0 ? Response.response(200) : Response.response(400);

    }

    /**
     * 导入学生名单时初始化选课时间，默认为导入名单时的时间
     *
     * @param startdate
     * @param enddate
     */
    public void initTime(Date startdate, Date enddate) {
        try {
            TopicTime topicTime = new TopicTime();
            topicTime.setStartDate(startdate);
            topicTime.setEndDate(enddate);
            topicTimeRepo.save(topicTime);
        } catch (Exception e) {
        }
    }

    /**
     * 判断当前时间是否在选课区段
     *
     * @param nowTime
     * @return
     */
    public boolean belongCalendar(Date nowTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(topicTimeRepo.queryStartTime());

        Calendar end = Calendar.getInstance();
        end.setTime(topicTimeRepo.QueryEndTime());

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
