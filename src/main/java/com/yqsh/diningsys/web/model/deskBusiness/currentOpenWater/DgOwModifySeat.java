package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DgOwModifySeat {
    private Integer id;

    private String owNum; //流水号

    private Integer orgSeatId; //更换前桌位id

    private Integer nowSeatId; //更换后桌位id

    private Double orgFwf; //服务费

    private Double orgBff; //包房费

    private Integer isGgbff; //是否更改为目标桌位id
    
    private Date time; //更换桌位时间
    
    private Integer isJsbffpx; //是否加入包房费品项

    
    private String seatName;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwNum() {
        return owNum;
    }

    public void setOwNum(String owNum) {
        this.owNum = owNum == null ? null : owNum.trim();
    }

    public Integer getOrgSeatId() {
        return orgSeatId;
    }

    public void setOrgSeatId(Integer orgSeatId) {
        this.orgSeatId = orgSeatId;
    }

    public Integer getNowSeatId() {
        return nowSeatId;
    }

    public void setNowSeatId(Integer nowSeatId) {
        this.nowSeatId = nowSeatId;
    }

    public Double getOrgFwf() {
        return orgFwf;
    }

    public void setOrgFwf(Double orgFwf) {
        this.orgFwf = orgFwf;
    }

    public Double getOrgBff() {
        return orgBff;
    }

    public void setOrgBff(Double orgBff) {
        this.orgBff = orgBff;
    }

    public Integer getIsGgbff() {
        return isGgbff;
    }

    public void setIsGgbff(Integer isGgbff) {
        this.isGgbff = isGgbff;
    }

	public String getTime() {
		return (time != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time):null;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getIsJsbffpx() {
		return isJsbffpx;
	}

	public void setIsJsbffpx(Integer isJsbffpx) {
		this.isJsbffpx = isJsbffpx;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
    
    
}