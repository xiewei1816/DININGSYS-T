package com.yqsh.diningsys.web.model.businessMan;

public class DgAreaManager {
    private Integer id;

    private Integer sjDiscount; //时价品项允许打折

    private Integer cxDiscount; //促销品项允许打折

    private Integer bjDiscount; //变价品项允许打折

    private Integer bffDiscount; //包房费允许打折

    private Integer fwfDiscount; //服务费允许打折

    private Integer zdxfDiscount; //最低消费允许打折

    private Integer bffFree; //包房费免费时长

    private Integer bffTimeFree; //包房费多长时间内免费

    private Integer bffSurplusRemind; //包房剩余时间提醒

    private Integer areaId; //区域id

    
    /**
     * 查询字段
     * 
     */
    private String code;//区域编号
    private String name;//区域名称
    private Integer limCount;//限售品项总数
    
    
    public DgAreaManager(Integer sjDiscount,Integer cxDiscount,Integer bjDiscount,Integer bffDiscount,Integer fwfDiscount,Integer zdxfDiscount,Integer bffFree,Integer areaId)
    {
    	this.sjDiscount = sjDiscount;
    	this.cxDiscount = cxDiscount;
    	this.bjDiscount = bjDiscount;
    	this.bffDiscount = bffDiscount;
    	this.fwfDiscount = fwfDiscount;
    	this.zdxfDiscount = zdxfDiscount;
    	this.bffFree = bffFree;
    	this.areaId = areaId;
    }
    public DgAreaManager()
    {
    	
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSjDiscount() {
        return sjDiscount;
    }

    public void setSjDiscount(Integer sjDiscount) {
        this.sjDiscount = sjDiscount;
    }

    public Integer getCxDiscount() {
        return cxDiscount;
    }

    public void setCxDiscount(Integer cxDiscount) {
        this.cxDiscount = cxDiscount;
    }

    public Integer getBjDiscount() {
        return bjDiscount;
    }

    public void setBjDiscount(Integer bjDiscount) {
        this.bjDiscount = bjDiscount;
    }

    public Integer getBffDiscount() {
        return bffDiscount;
    }

    public void setBffDiscount(Integer bffDiscount) {
        this.bffDiscount = bffDiscount;
    }

    public Integer getFwfDiscount() {
        return fwfDiscount;
    }

    public void setFwfDiscount(Integer fwfDiscount) {
        this.fwfDiscount = fwfDiscount;
    }

    public Integer getZdxfDiscount() {
        return zdxfDiscount;
    }

    public void setZdxfDiscount(Integer zdxfDiscount) {
        this.zdxfDiscount = zdxfDiscount;
    }

    public Integer getBffFree() {
        return bffFree;
    }

    public void setBffFree(Integer bffFree) {
        this.bffFree = bffFree;
    }

    public Integer getBffTimeFree() {
        return bffTimeFree;
    }

    public void setBffTimeFree(Integer bffTimeFree) {
        this.bffTimeFree = bffTimeFree;
    }

    public Integer getBffSurplusRemind() {
        return bffSurplusRemind;
    }

    public void setBffSurplusRemind(Integer bffSurplusRemind) {
        this.bffSurplusRemind = bffSurplusRemind;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLimCount() {
		return limCount;
	}

	public void setLimCount(Integer limCount) {
		this.limCount = limCount;
	}
	
	
}