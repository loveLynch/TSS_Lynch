package com.silver.tss.service;

import com.alibaba.fastjson.JSONObject;
import com.silver.tss.common.Response;
import com.silver.tss.domain.Status;
import com.silver.tss.repository.StatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by lynch on 2018/10/21. <br>
 **/
@Service
public class StatusService {

    @Autowired
    private StatusRepo statusRepo;


    public Boolean isStatus0() {
        List<Status> statuses = getStatusList();
        return statuses.size() > 0 && statuses.get(0).getTssstatus() == 0;
    }

    public Boolean isStatus1() {
        List<Status> statuses = getStatusList();
        return statuses.size() > 0 && statuses.get(0).getTssstatus() == 1;
    }

    public Boolean isStatus2() {
        List<Status> statuses = getStatusList();
        return statuses.size() > 0 && statuses.get(0).getTssstatus() == 2;
    }

    public Boolean isStatus3() {
        List<Status> statuses = getStatusList();
        return statuses.size() > 0 && statuses.get(0).getTssstatus() == 3;
    }

    public JSONObject updateStatus(int status) {
        List<Status> list = null;
        try {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setTssstatus(status);
                list.get(i).setModifyTime(new Date());
            }
        } catch (Exception e) {
        }
        return list.size() > 0 ? Response.response(200) : Response.response(400);
    }

    private List<Status> getStatusList() {
        List<Status> list = null;
        try {
            list = statusRepo.findAll();
        } catch (Exception e) {
        }
        return list;
    }
}
