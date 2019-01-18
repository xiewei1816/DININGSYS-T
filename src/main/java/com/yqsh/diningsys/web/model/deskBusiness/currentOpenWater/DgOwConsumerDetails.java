package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DgOwConsumerDetails {
    private Integer itemFileId;

    private Double itemFileNumber;

    private Double itemFileZs;

    private Double productionCosts;

    private Double discount;

    private Double subtotal;

    private Integer owId;

    private String guest;

    private String notes;

    private Integer servingType;

    private Integer servingTypeGlobal;

    private Date expectationsServingTime;

    private Integer servingCase;

    private Integer isTc;

    private Integer parentId;


    //品项关联
    private String itemFileName;

    private String itemFileNum;

    private Double standardPrice;

    private String unit;

    /**
     * 品项加单时的最终价格
     */
    private Double itemFinalPrice;
    
    
    private Integer backOwId;
    
    
    private Integer reminderNumber;
    
    
    private Integer qcZt;
    private Integer qcFs;
    private Date qcZhsj;
    private Double qcSl;

    /**
     * 制作费用 
     */
    private Double sumCosts;

    /**
     * 参与最低消费
     */
    private Integer cyzdxf;

    /**
     * 是否变价
     */
    private Integer variablePrice;

    /**
     * 初始价格
     */
    private Double initialPrice;



    /**
     * 赠送计算价格
     */
    private Double zsItemFinalPrice;
    private Double zsProductionCosts;
    private Double zsSubtotal;
    private Integer isPriceCal;

    /**
     * 重要活动品项价格
     */
    private Double zyhdItemFilePrice;

    private Double zyhdItemFilePriceDiscount;

    /**
     * 重要活动品项价格和
     */
    private Double zyhdItemCostsSum;

    private Double zyhdItemCostsSumDiscount;

    /**
     * 品项打折品项价格
     */
    private Double pxdzItemFilePrice;

    private Double pxdzItemFilePriceDiscount;

    /**
     * 品项打折品项价格和
     */
    private Double pxdzItemCostsSum;

    private Double pxdzItemCostsSumDiscount;

    /**
     * 会员打折品项价格
     */
    private Double hydzItemFilePrice;

    /**
     * 会员打折品项价格和
     */
    private Double hydzItemCostsSum;

    /**
     * 品项买单时的最终价格
     */
    private Double itemPayMoney;

    /**
     * 优惠的金额
     */
    private BigDecimal discountMoney;

    /**
     * 应收总额
     */
    private BigDecimal totalReceivables;

    /**
     * 净收入
     */
    private BigDecimal netIncome;

    private String smallTypeName;

    private String bigTypeName;

    /**
     * 品项对应的品项所属大类ID
     */
    private Integer pxdlId;

    /**
     * 埋单时的价格信息
     */
    private String payPriceInfo;

    /**
     * 优惠券关联的品项大类
     */
    private String couponItemType;

    private Integer newServiceId;

    private Integer yxdz;

    /**
     * 该品项是否已经被结算
     * value：0|1
     */
    private Integer settlementStatus;

    /**
     * 品项大类名称
     */
    private String pxdlName;

    private String firstTime;

    //2017年10月17日17:14:14  因为券的支付方式新增一下字段

    /**
     * 品项小类ID
     */
    private Integer pxxlId;

    /**
     * 品项小类名称
     */
    private String pxxlName;

    /**
     * 打折前合计
     */
    private Double dzqTotalMoney;

    /**
     * 是否是易小二加单
     */
    private Integer addByYxe;

    //2018-12-04 优惠券
    //关联优惠券
    private String couponVal;

    //优惠前金额
    private Double couponBfPrice;

    //优惠数量
    private Integer couponNum;

    //减单数量
    private Double subNumber;

    public Double getDzqTotalMoney() {
		return dzqTotalMoney;
	}

	public void setDzqTotalMoney(Double dzqTotalMoney) {
		this.dzqTotalMoney = dzqTotalMoney;
	}

	public Integer getPxxlId() {
        return pxxlId;
    }

    public void setPxxlId(Integer pxxlId) {
        this.pxxlId = pxxlId;
    }

    public String getPxxlName() {
        return pxxlName;
    }

    public void setPxxlName(String pxxlName) {
        this.pxxlName = pxxlName;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getPxdlName() {
        return pxdlName;
    }

    public void setPxdlName(String pxdlName) {
        this.pxdlName = pxdlName;
    }

    public Integer getYxdz() {
        return yxdz;
    }

    public void setYxdz(Integer yxdz) {
        this.yxdz = yxdz;
    }

    public Integer getNewServiceId() {
        return newServiceId;
    }

    public void setNewServiceId(Integer newServiceId) {
        this.newServiceId = newServiceId;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Double getItemFileNumber() {
        return itemFileNumber;
    }

    public void setItemFileNumber(Double itemFileNumber) {
        this.itemFileNumber = itemFileNumber;
    }

    public Double getItemFileZs() {
        return itemFileZs;
    }

    public void setItemFileZs(Double itemFileZs) {
        this.itemFileZs = itemFileZs;
    }

    public Double getProductionCosts() {
        return productionCosts==null ? 0.0 : productionCosts;
    }

    public void setProductionCosts(Double productionCosts) {
        this.productionCosts = productionCosts;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getOwId() {
        return owId;
    }

    public void setOwId(Integer owId) {
        this.owId = owId;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest == null ? null : guest.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Integer getServingType() {
        return servingType;
    }

    public void setServingType(Integer servingType) {
        this.servingType = servingType;
    }

    public Integer getServingTypeGlobal() {
        return servingTypeGlobal;
    }

    public void setServingTypeGlobal(Integer servingTypeGlobal) {
        this.servingTypeGlobal = servingTypeGlobal;
    }

    public String getExpectationsServingTime() {
        return expectationsServingTime != null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expectationsServingTime):null;
    }

    public void setExpectationsServingTime(Date expectationsServingTime) {
        this.expectationsServingTime = expectationsServingTime;
    }

    public Integer getServingCase() {
        return servingCase;
    }

    public void setServingCase(Integer servingCase) {
        this.servingCase = servingCase;
    }

	public String getItemFileName() {
		return itemFileName;
	}

	public void setItemFileName(String itemFileName) {
		this.itemFileName = itemFileName;
	}

	public String getItemFileNum() {
		return itemFileNum;
	}

	public void setItemFileNum(String itemFileNum) {
		this.itemFileNum = itemFileNum;
	}

	public Double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getIsTc() {
		return isTc;
	}

	public void setIsTc(Integer isTc) {
		this.isTc = isTc;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

    public Double getItemFinalPrice() {
        return itemFinalPrice;
    }

    public void setItemFinalPrice(Double itemFinalPrice) {
        this.itemFinalPrice = itemFinalPrice;
    }

	public Integer getBackOwId() {
		return backOwId;
	}

	public void setBackOwId(Integer backOwId) {
		this.backOwId = backOwId;
	}

	public Integer getReminderNumber() {
		return reminderNumber;
	}

	public void setReminderNumber(Integer reminderNumber) {
		this.reminderNumber = reminderNumber;
	}

	public Integer getQcZt() {
		return qcZt;
	}

	public void setQcZt(Integer qcZt) {
		this.qcZt = qcZt;
	}

	public Integer getQcFs() {
		return qcFs;
	}

	public void setQcFs(Integer qcFs) {
		this.qcFs = qcFs;
	}

	public String getQcZhsj() {
		return (qcZhsj != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qcZhsj):null;
	}

	public void setQcZhsj(Date qcZhsj) {
		this.qcZhsj = qcZhsj;
	}

	public Double getQcSl() {
		return qcSl;
	}

	public void setQcSl(Double qcSl) {
		this.qcSl = qcSl;
	}

    public Double getSumCosts() {
        return sumCosts;
    }

    public void setSumCosts(Double sumCosts) {
        this.sumCosts = sumCosts;
    }

    public Integer getVariablePrice() {
        return variablePrice;
    }

    public void setVariablePrice(Integer variablePrice) {
        this.variablePrice = variablePrice;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

	public Double getZsItemFinalPrice() {
		return zsItemFinalPrice;
	}

	public void setZsItemFinalPrice(Double zsItemFinalPrice) {
		this.zsItemFinalPrice = zsItemFinalPrice;
	}

	public Double getZsProductionCosts() {
		return zsProductionCosts;
	}

	public void setZsProductionCosts(Double zsProductionCosts) {
		this.zsProductionCosts = zsProductionCosts;
	}

	public Double getZsSubtotal() {
		return zsSubtotal;
	}

	public void setZsSubtotal(Double zsSubtotal) {
		this.zsSubtotal = zsSubtotal;
	}

	public Integer getIsPriceCal() {
		return isPriceCal;
	}

	public void setIsPriceCal(Integer isPriceCal) {
		this.isPriceCal = isPriceCal;
	}

    public Double getItemPayMoney() {
        return itemPayMoney;
    }

    public void setItemPayMoney(Double itemPayMoney) {
        this.itemPayMoney = itemPayMoney;
    }

    public Double getZyhdItemFilePrice() {
        return zyhdItemFilePrice;
    }

    public void setZyhdItemFilePrice(Double zyhdItemFilePrice) {
        this.zyhdItemFilePrice = zyhdItemFilePrice;
    }

    public Double getZyhdItemCostsSum() {
        return zyhdItemCostsSum;
    }

    public void setZyhdItemCostsSum(Double zyhdItemCostsSum) {
        this.zyhdItemCostsSum = zyhdItemCostsSum;
    }

    public Double getPxdzItemFilePrice() {
        return pxdzItemFilePrice;
    }

    public void setPxdzItemFilePrice(Double pxdzItemFilePrice) {
        this.pxdzItemFilePrice = pxdzItemFilePrice;
    }

    public Double getPxdzItemCostsSum() {
        return pxdzItemCostsSum;
    }

    public void setPxdzItemCostsSum(Double pxdzItemCostsSum) {
        this.pxdzItemCostsSum = pxdzItemCostsSum;
    }

    public Double getHydzItemFilePrice() {
        return hydzItemFilePrice;
    }

    public void setHydzItemFilePrice(Double hydzItemFilePrice) {
        this.hydzItemFilePrice = hydzItemFilePrice;
    }

    public Double getHydzItemCostsSum() {
        return hydzItemCostsSum;
    }

    public void setHydzItemCostsSum(Double hydzItemCostsSum) {
        this.hydzItemCostsSum = hydzItemCostsSum;
    }

    public Integer getPxdlId() {
        return pxdlId;
    }

    public void setPxdlId(Integer pxdlId) {
        this.pxdlId = pxdlId;
    }

    public String getPayPriceInfo() {
        return payPriceInfo;
    }

    public void setPayPriceInfo(String payPriceInfo) {
        this.payPriceInfo = payPriceInfo;
    }

    public Double getZyhdItemFilePriceDiscount() {
        return zyhdItemFilePriceDiscount;
    }

    public void setZyhdItemFilePriceDiscount(Double zyhdItemFilePriceDiscount) {
        this.zyhdItemFilePriceDiscount = zyhdItemFilePriceDiscount;
    }

    public Double getZyhdItemCostsSumDiscount() {
        return zyhdItemCostsSumDiscount;
    }

    public void setZyhdItemCostsSumDiscount(Double zyhdItemCostsSumDiscount) {
        this.zyhdItemCostsSumDiscount = zyhdItemCostsSumDiscount;
    }

    public Double getPxdzItemFilePriceDiscount() {
        return pxdzItemFilePriceDiscount;
    }

    public void setPxdzItemFilePriceDiscount(Double pxdzItemFilePriceDiscount) {
        this.pxdzItemFilePriceDiscount = pxdzItemFilePriceDiscount;
    }

    public Double getPxdzItemCostsSumDiscount() {
        return pxdzItemCostsSumDiscount;
    }

    public void setPxdzItemCostsSumDiscount(Double pxdzItemCostsSumDiscount) {
        this.pxdzItemCostsSumDiscount = pxdzItemCostsSumDiscount;
    }

    public String getCouponItemType() {
        return couponItemType;
    }

    public void setCouponItemType(String couponItemType) {
        this.couponItemType = couponItemType;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getTotalReceivables() {
        return totalReceivables;
    }

    public void setTotalReceivables(BigDecimal totalReceivables) {
        this.totalReceivables = totalReceivables;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

    public String getSmallTypeName() {
        return smallTypeName;
    }

    public void setSmallTypeName(String smallTypeName) {
        this.smallTypeName = smallTypeName;
    }

    public String getBigTypeName() {
        return bigTypeName;
    }

    public void setBigTypeName(String bigTypeName) {
        this.bigTypeName = bigTypeName;
    }

    public Integer getCyzdxf() {
        return cyzdxf;
    }

    public void setCyzdxf(Integer cyzdxf) {
        this.cyzdxf = cyzdxf;
    }

    public Integer getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(Integer settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public Integer getAddByYxe() {
        return addByYxe;
    }

    public void setAddByYxe(Integer addByYxe) {
        this.addByYxe = addByYxe;
    }

    public String getCouponVal() {
        return couponVal;
    }

    public void setCouponVal(String couponVal) {
        this.couponVal = couponVal;
    }

    public Double getCouponBfPrice() {
        return couponBfPrice;
    }

    public void setCouponBfPrice(Double couponBfPrice) {
        this.couponBfPrice = couponBfPrice;
    }

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public Double getSubNumber() {
        return subNumber;
    }

    public void setSubNumber(Double subNumber) {
        this.subNumber = subNumber;
    }
}