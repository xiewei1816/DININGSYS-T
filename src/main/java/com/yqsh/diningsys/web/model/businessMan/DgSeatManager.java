package com.yqsh.diningsys.web.model.businessMan;

public class DgSeatManager {
    private Integer id;

    private Integer zdxf; //最低消费 类型  (1:不收取  2:按zdxfType收取)

    private Integer zdxfType; //最低消费  (按每客位 1/每客位人数类型 2)

    private Double zdxfMoney; //最低消费金额

    private Integer fwf; //服务费 类型  (1 不收取  / 2 按固定金额  /3 按消费比例  /4 按客位人数)

    private Integer fwfType;  //-------停用

    private Double fwfGd; //服务费固定金额

    private Integer fwfXfRatio; //服务费消费比例

    private Double fwfConFree; //服务费消费满多少免费

    private Double fwfSx; //服务费上限

    private Double fwfPeople; //包房费客位人数收取

    private Integer qssc; //清扫时长

    private Integer qsscTx; //启动清扫时长语音提醒功能

    private Integer bff; //包房费类型  (1 不收取 /2   按固定金额  /3  按消费比例 / 4 固定包房收费方案 /5  一周内设置不同的包房收费方案)

    private Double bffGd; //包房费固定金额收取

    private Double bffPeople; //包房费客位人数收取

    private Integer bffGdPro; //固定包房费收费方案对应id(具体数据子表关联)

    private Integer bffWeekPro; //一周内设置不同的包房费方案

    private Integer bffItemId; //包房费品项id

    private Double bffConFree; //包房费消费满多少不收取

    private Integer bffTiming; //是否启动包房计时功能

    private Integer seatId; //客位id

    
    private String bffItemName;//包房费品项名称
    private String bffWeekProD;//周中每天对应包房费方案
    
    
    /**
     * 额外查询字段
     */
    private String seatCode;//客位编号
    private String seatName;//客位名称
    private String areaString;//所属消费区域
    private Integer itemCount;//开单自动增加品项数目
    private Integer bffType;
    
    
    public DgSeatManager()
    {
    	
    }
    
    public DgSeatManager(Integer zdxf,Integer fwf,Integer qssc,Integer bff,Integer seatId)
    {
    	this.zdxf = zdxf;
    	this.fwf = fwf;
    	this.qssc = qssc;
    	this.bff = bff;
    	this.seatId = seatId;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZdxf() {
        return zdxf;
    }

    public void setZdxf(Integer zdxf) {
        this.zdxf = zdxf;
    }

    public Integer getZdxfType() {
        return zdxfType;
    }

    public void setZdxfType(Integer zdxfType) {
        this.zdxfType = zdxfType;
    }

    public Double getZdxfMoney() {
        return zdxfMoney;
    }

    public void setZdxfMoney(Double zdxfMoney) {
        this.zdxfMoney = zdxfMoney;
    }

    public Integer getFwf() {
        return fwf;
    }

    public void setFwf(Integer fwf) {
        this.fwf = fwf;
    }

    public Integer getFwfType() {
        return fwfType;
    }

    public void setFwfType(Integer fwfType) {
        this.fwfType = fwfType;
    }

    public Double getFwfGd() {
        return fwfGd;
    }

    public void setFwfGd(Double fwfGd) {
        this.fwfGd = fwfGd;
    }

    public Integer getFwfXfRatio() {
        return fwfXfRatio;
    }

    public void setFwfXfRatio(Integer fwfXfRatio) {
        this.fwfXfRatio = fwfXfRatio;
    }

    public Double getFwfConFree() {
        return fwfConFree;
    }

    public void setFwfConFree(Double fwfConFree) {
        this.fwfConFree = fwfConFree;
    }

    public Double getFwfSx() {
        return fwfSx==null?0.0:fwfSx;
    }

    public void setFwfSx(Double fwfSx) {
        this.fwfSx = fwfSx;
    }

    public Double getFwfPeople() {
        return fwfPeople;
    }

    public void setFwfPeople(Double fwfPeople) {
        this.fwfPeople = fwfPeople;
    }

    public Integer getQssc() {
        return qssc;
    }

    public void setQssc(Integer qssc) {
        this.qssc = qssc;
    }

    public Integer getQsscTx() {
        return qsscTx;
    }

    public void setQsscTx(Integer qsscTx) {
        this.qsscTx = qsscTx;
    }

    public Integer getBff() {
        return bff;
    }

    public void setBff(Integer bff) {
        this.bff = bff;
    }

    public Double getBffGd() {
        return bffGd;
    }

    public void setBffGd(Double bffGd) {
        this.bffGd = bffGd;
    }

    public Double getBffPeople() {
        return bffPeople;
    }

    public void setBffPeople(Double bffPeople) {
        this.bffPeople = bffPeople;
    }

    public Integer getBffGdPro() {
        return bffGdPro;
    }

    public void setBffGdPro(Integer bffGdPro) {
        this.bffGdPro = bffGdPro;
    }

    public Integer getBffWeekPro() {
        return bffWeekPro;
    }

    public void setBffWeekPro(Integer bffWeekPro) {
        this.bffWeekPro = bffWeekPro;
    }

    public Integer getBffItemId() {
        return bffItemId;
    }

    public void setBffItemId(Integer bffItemId) {
        this.bffItemId = bffItemId;
    }

    public Double getBffConFree() {
        return bffConFree==null?0.0:bffConFree;
    }

    public void setBffConFree(Double bffConFree) {
        this.bffConFree = bffConFree;
    }

    public Integer getBffTiming() {
        return bffTiming;
    }

    public void setBffTiming(Integer bffTiming) {
        this.bffTiming = bffTiming;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

	public String getBffItemName() {
		return bffItemName;
	}

	public void setBffItemName(String bffItemName) {
		this.bffItemName = bffItemName;
	}

	public String getBffWeekProD() {
		return bffWeekProD;
	}

	public void setBffWeekProD(String bffWeekProD) {
		this.bffWeekProD = bffWeekProD;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getAreaString() {
		return areaString;
	}

	public void setAreaString(String areaString) {
		this.areaString = areaString;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Integer getBffType() {
		return bffType;
	}

	public void setBffType(Integer bffType) {
		this.bffType = bffType;
	}
	
	
}