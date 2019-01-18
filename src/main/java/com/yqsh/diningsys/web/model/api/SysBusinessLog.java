package com.yqsh.diningsys.web.model.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class SysBusinessLog extends BasePojo{
    private String uuid;

    private String oper; //操作人 (姓名)

    private String pos; //操作pos (pos名称)

    private Date time;  //操作时间

    private Integer type;  //类型(1/加入团队  2/转账  3/退出团队  4/撤销转账  5/赠单  6/埋单)

    private String owNum;  //营业流水号

    private String seatName;  //客位号

    private String content;  //主内容
    
    
    
    private String table_end; // 日志表尾部
    
    
    public SysBusinessLog(){
    	
    	
    }
    
    
    public SysBusinessLog(String uuid,String oper,String pos,Date time,Integer type,String owNum,String seatName,String content,String table_end){
    	this.uuid = uuid;
    	this.oper = oper;
    	this.pos = pos;
    	this.time = time;
    	this.type = type;
    	this.owNum = owNum;
    	this.seatName = seatName;
    	this.content = content;
    	this.table_end = table_end;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper == null ? null : oper.trim();
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos == null ? null : pos.trim();
    }

    public String getTime() {
        return (time != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time):null;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOwNum() {
        return owNum;
    }

    public void setOwNum(String owNum) {
        this.owNum = owNum == null ? null : owNum.trim();
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName == null ? null : seatName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


	public String getTable_end() {
		return table_end;
	}


	public void setTable_end(String table_end) {
		this.table_end = table_end;
	}
    
    
}