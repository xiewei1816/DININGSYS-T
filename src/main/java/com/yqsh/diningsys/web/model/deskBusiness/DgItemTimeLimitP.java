package com.yqsh.diningsys.web.model.deskBusiness;

import java.util.Date;

public class DgItemTimeLimitP {
    private Integer id;

    private String endTime;

    private String startTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}