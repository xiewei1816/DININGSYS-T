package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

public class DgOffset {
    private Integer id;

    private String time;

    private String content;

    private Integer type;

    public DgOffset(){

    }
    public DgOffset(String time,String content,Integer type){
        this.time = time;
        this.content = content;
        this.type = type;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}