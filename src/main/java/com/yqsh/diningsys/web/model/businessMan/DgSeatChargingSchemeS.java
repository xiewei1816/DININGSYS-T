package com.yqsh.diningsys.web.model.businessMan;

public class DgSeatChargingSchemeS {
    private Integer id;

    private Integer pId; //父id

    private Integer tLong; //时长，多少分钟

    private Double money; //收费金额
 
    private Integer sd;  //时段   对应数据字典id 32

    private Integer discount; //折扣
    
    
    private String sdName; //时段名称  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer gettLong() {
        return tLong;
    }

    public void settLong(Integer tLong) {
        this.tLong = tLong;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getSd() {
        return sd;
    }

    public void setSd(Integer sd) {
        this.sd = sd;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String sdName) {
		this.sdName = sdName;
	}
    
    
}