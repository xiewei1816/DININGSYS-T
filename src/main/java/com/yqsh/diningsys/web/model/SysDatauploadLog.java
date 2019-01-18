package com.yqsh.diningsys.web.model;

import java.util.Date;

/**
 * Created on 2017-04-26 14:47
 *
 * @author zhshuo
 */
public class SysDatauploadLog {

    private String id;

    private String time;

    private Integer type;

    public SysDatauploadLog() {
    }

    public SysDatauploadLog(String id, String time, Integer type) {
        this.id = id;
        this.time = time;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
