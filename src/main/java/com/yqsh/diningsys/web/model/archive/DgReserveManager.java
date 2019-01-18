package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgReserveManager extends BasePojo{
    private Integer id;

    private String crId;

    private Integer seatId;

    private Integer number;

    private String phone;

    private Date ydTime;

    private String ydNum;

    private Integer state = 0;

    private String ydResoure;

    private Integer xszf;

    private Double xszfMoney;

    private Integer xszfType;

    private Date time;
    
    private String name;
    
    private Integer sex;
    
    //模糊查询字段
    private String search;
    
    private String seatName;  
    
    private Integer posId; //posId
    
    private Integer wOw;//午餐或晚餐
    
    private Integer bsd;//没有包间是否选择大厅
   
    private List<DgReserveSeatids> childIds;
    
    //pos关联的消费区域
    private List<Integer> posConsumerIds;
    
    //包间id组合
    private List<Integer> bjIds;
     
    //线上预定消息(关联call id)
    private int onlineMsg;
    
    private String searchTime;
    
    private Integer interTime;//预定间隔时间
    
    //表查询
    private String tableEnd;
    
	public Integer getInterTime() {
		return interTime;
	}

	public void setInterTime(Integer interTime) {
		this.interTime = interTime;
	}

	public int getOnlineMsg() {
		return onlineMsg;
	}

	public void setOnlineMsg(int onlineMsg) {
		this.onlineMsg = onlineMsg;
	}

	
	public List<Integer> getBjIds() {
		return bjIds;
	}

	public void setBjIds(List<Integer> bjIds) {
		this.bjIds = bjIds;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId == null ? null : crId.trim();
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getYdTime() {
    	return (ydTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ydTime):null;
    }

    public void setYdTime(Date ydTime) {
        this.ydTime = ydTime;
    }

    public String getYdNum() {
        return ydNum;
    }

    public void setYdNum(String ydNum) {
        this.ydNum = ydNum == null ? null : ydNum.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getYdResoure() {
        return ydResoure;
    }

    public void setYdResoure(String ydResoure) {
        this.ydResoure = ydResoure;
    }

    public Integer getXszf() {
        return xszf;
    }

    public void setXszf(Integer xszf) {
        this.xszf = xszf;
    }

    public Double getXszfMoney() {
        return xszfMoney;
    }

    public void setXszfMoney(Double xszfMoney) {
        this.xszfMoney = xszfMoney;
    }

    public Integer getXszfType() {
        return xszfType;
    }

    public void setXszfType(Integer xszfType) {
        this.xszfType = xszfType;
    }

    public String getTime() {
    	 return (time != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time):null;
    }

    public void setTime(Date time) {
        this.time = time;
    }

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public List<DgReserveSeatids> getChildIds() {
		return childIds;
	}

	public void setChildIds(List<DgReserveSeatids> childIds) {
		this.childIds = childIds;
	}

	public Integer getPosId() {
		return posId;
	}

	public void setPosId(Integer posId) {
		this.posId = posId;
	}

	public List<Integer> getPosConsumerIds() {
		return posConsumerIds;
	}

	public void setPosConsumerIds(List<Integer> posConsumerIds) {
		this.posConsumerIds = posConsumerIds;
	}

	public Integer getwOw() {
		return wOw;
	}

	public void setwOw(Integer wOw) {
		this.wOw = wOw;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	public Integer getBsd() {
		return bsd;
	}

	public void setBsd(Integer bsd) {
		this.bsd = bsd;
	}

	public String getTableEnd() {
		return tableEnd;
	}

	public void setTableEnd(String tableEnd) {
		this.tableEnd = tableEnd;
	}		
	
	
	
}