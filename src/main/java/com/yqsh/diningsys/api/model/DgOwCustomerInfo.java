package com.yqsh.diningsys.api.model;

import java.util.Date;

/**
 * 营业流水顾客信息
 *
 * @author zhshuo create on 2016-12-20 16:41
 */
public class DgOwCustomerInfo {

    /**
     * 营业流水ID
     */
    private Integer owId;

    /**
     * 营业流水号
     */
    private String owNum;

    /**
     * 年龄范围
     */
    private String ageRange;

    /**
     * 男人数
     */
    private Integer manCount;

    /**
     * 女人数
     */
    private Integer femaleCount;

    /**
     * 外宾人数
     */
    private Integer forgenCount;

    /**
     * 登记时间
     */
    private Date regTime;

    public Integer getOwId() {
        return owId;
    }

    public void setOwId(Integer owId) {
        this.owId = owId;
    }

    public String getOwNum() {
        return owNum;
    }

    public void setOwNum(String owNum) {
        this.owNum = owNum;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public Integer getManCount() {
        return manCount;
    }

    public void setManCount(Integer manCount) {
        this.manCount = manCount;
    }

    public Integer getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(Integer femaleCount) {
        this.femaleCount = femaleCount;
    }

    public Integer getForgenCount() {
        return forgenCount;
    }

    public void setForgenCount(Integer forgenCount) {
        this.forgenCount = forgenCount;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
}
