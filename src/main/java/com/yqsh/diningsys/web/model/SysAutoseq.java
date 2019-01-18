package com.yqsh.diningsys.web.model;

public class SysAutoseq {
    private Integer id;
    //总位数  例如0001  4位
    private Integer rownum;
    //当前序列号 1
    private Integer currentnum;
    //是否有父节点
    private Integer hasparent;
    //父节点序号
    private String parent;
    //补齐头部数据 TX   TX0001
    private String head;
    //是否补齐头部
    private Integer hashead;
    //type分类
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public Integer getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(Integer currentnum) {
        this.currentnum = currentnum;
    }

    public Integer getHasparent() {
        return hasparent;
    }

    public void setHasparent(Integer hasparent) {
        this.hasparent = hasparent;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public Integer getHashead() {
        return hashead;
    }

    public void setHashead(Integer hashead) {
        this.hashead = hashead;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}