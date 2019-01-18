package com.yqsh.diningsys.web.model;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * Created on 2017-05-19 10:54
 * 授权日志
 * @author zhshuo
 */
public class SysAuthorizationLog  extends BasePojo {

    private String id;

    private String authTime;

    private String authCode;

    private String authOpUser;

    private String authUser;

    private String authRemarks;

    private String startTime;

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public SysAuthorizationLog() {
    }

    public SysAuthorizationLog(String authCode, String authOpUser, String authUser, String authRemarks) {
        this.authCode = authCode;
        this.authOpUser = authOpUser;
        this.authUser = authUser;
        this.authRemarks = authRemarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthOpUser() {
        return authOpUser;
    }

    public void setAuthOpUser(String authOpUser) {
        this.authOpUser = authOpUser;
    }

    public String getAuthUser() {
        return authUser;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public String getAuthRemarks() {
        return authRemarks;
    }

    public void setAuthRemarks(String authRemarks) {
        this.authRemarks = authRemarks;
    }

}
