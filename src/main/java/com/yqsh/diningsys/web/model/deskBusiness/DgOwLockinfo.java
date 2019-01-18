package com.yqsh.diningsys.web.model.deskBusiness;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 营业流水锁定
 *
 * @author zhshuo create on 2016-12-29 19:00
 */
public class DgOwLockinfo {

    private String owNum;

    private Integer owState;

    private Date lockTime;

    private String lockOperator;

    private String lockOpeartorName;

    private Integer lockPos;

    private String lockPosName;

    private String lockNotes;

    private Integer lockType;

    public String getOwNum() {
        return owNum;
    }

    public void setOwNum(String owNum) {
        this.owNum = owNum;
    }

    public Integer getOwState() {
        return owState;
    }

    public void setOwState(Integer owState) {
        this.owState = owState;
    }

    public String getLockTime() {
        return (lockTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockTime):null;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public String getLockOperator() {
        return lockOperator;
    }

    public void setLockOperator(String lockOperator) {
        this.lockOperator = lockOperator;
    }

    public Integer getLockPos() {
        return lockPos;
    }

    public void setLockPos(Integer lockPos) {
        this.lockPos = lockPos;
    }

    public String getLockNotes() {
        return lockNotes;
    }

    public void setLockNotes(String lockNotes) {
        this.lockNotes = lockNotes;
    }

    public String getLockOpeartorName() {
        return lockOpeartorName;
    }

    public void setLockOpeartorName(String lockOpeartorName) {
        this.lockOpeartorName = lockOpeartorName;
    }

    public String getLockPosName() {
        return lockPosName;
    }

    public void setLockPosName(String lockPosName) {
        this.lockPosName = lockPosName;
    }

    public Integer getLockType() {
        return lockType;
    }

    public void setLockType(Integer lockType) {
        this.lockType = lockType;
    }
}
