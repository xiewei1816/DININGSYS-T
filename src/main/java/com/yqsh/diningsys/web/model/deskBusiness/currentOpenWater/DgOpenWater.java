package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import com.yqsh.diningsys.api.model.DgBigItemTypeInfoList;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DgOpenWater{

    private String transferInfo;

    private Integer id;

    private String owNum;

    private Integer seatId;

    private Integer peopleCount;

    private Double itemCostsSum;

    private Double discountCosts;

    private Double serviceCharge;

    private Double privateRoomCost;

    private Double minimumConsumption;

    /**
     * 最低消费补齐
     */
    private Double minimumChargeComplete;

    /**
     * 品项打折最低消费补齐价格
     */
    private Double pxdzZdxfbq;

    /**
     * 重要活动最低消费补齐价格
     */
    private Double zyhdZdxfbq;

    /**
     * 会员打折最低消费价格

     */
    private Double hydzZdxfbq;

    private Double subtotal;

    private Integer clearingWaterId;

    private Date firstTime;

    /**
     * 开台市别
     */
    private String openBis;

    private String seatLabel;

    private Integer openPos;

    private Integer waiter;

    /**
     * 营销员
     */
    private Integer marketingStaff;

    private String crId;

    private String crName;

    private String documents;

    private String owNotes;

    private Date closedTime;
    
    /**
     * 团队标志
     */
    private String teamMembers;

    /**
     * 客座ID
     */
    private Integer seatId2;

    /**
     * 客座名称
     */
    private String seatName;

    /**
     * 客座号
     */
    private String tableno;
    /**
     * 客座的最大人数
     */
    private Integer allowNumber;

    /**
     * 客位状态
     */
    private Integer seatState;

    /**
     * 客位所属区域名称
     */
    private String areaName;

    /**
     * POS名称
     */
    private String posName;

    /**
     * 服务员名称
     */
    private String waiterName;

    /**
     * 是否为团队主客位
     */
    private Integer teamMainSeat;
    
    /**
     * 营业流水状态
     */
    private Integer OwState;

    /**
     * 是否为团队，0非团队，1团队
     */
    private Integer isTeam;

    private Integer seatAmount;

    /**
     * 转账流水目标
     */
    private String transferTarget;

    /**
     * 加入团队备注转账还是加入团队
     */
    private String joinTeamNotes;

    /**
     * 转账/加入团队 时间
     */
    private Date joinTeamTime;

    /**
     * 是否为拆账流水
     */
    private Integer splitOpenWater;

    /**
     * 从哪个流水拆账的号码
     */
    private String splitOpenWaterNum;

    /**
     * 拆账时间
     */
    private Date splitTime;

    private String splitTime2;

    /**
     * 服务员
     */
    private String waiterCode;

    /**
     * 营销员
     */
    private String marketingStaffCode;

    /**
     * 营销员名称
     */
    private String marketingStaffName;

    private Integer yjdPrintNum;

    private Integer kydPrintNum;

    /**
     * 营业流水挂S账的时间
     */
    private Date handingsbillTime;

    private Integer privateRoomType;

    /**
     * 开单的操作人员
     */
    private String openOperator;

    private String openOperatorName;

    /**
     * 是否存在自增品项
     */
    private Integer isIncreasingItem;

    /**
     * 营业流水下面的所有有效品项集合
     */
    private List<DgOwConsumerDetails> itemFileInfos;

    /**
     * 营业流水品项重要活动价格小计
     */
    private Double zyhdItemSubtotal;

    /**
     * 营业流水品项品项打折价格小计
     */
    private Double pxdzItemSubtotal;

    /**
     * 营业流水品项会员打折价格小计
     */
    private Double hydzItemSubtotal;

    /**
     * 重要活动价格小计
     */
    private Double zyhdSubtotal;

    /**
     * 品项打折价格小计
     */
    private Double pxdzSubtotal;

    /**
     * 会员打折价格小计
     */
    private Double hydzSubtotal;

    private Double paidMoney;

    /**
     * 押金
     */
    private Double deposit;

    /**
     * 会员信息
     */
    private Map<String,Object> memberInfo;

    /**
     * 会员券信息
     */
    private List<Map> memberCardList;

    private Double modifyServiceCharge;

    private Integer freeServceCharge;

    private Integer freeMinSpend;

    private Integer freePrivateRoom;

    /**
     * 是否已经免了包房费
     */
    private Boolean isFreePR = false;

    private Double modifyPrivateRoom;
    
    /**
     * 预定id
     */
    private Integer ydId;

    /**
     * 每一个营业流水下的品项数据汇总
     * 多个品项大类，每个大类下面有多个品项
     */
    private List<DgBigItemTypeInfoList> itemFileByBigTypes;

    /**
     * 买单时该营业流水的最终品项消费
     */
    private BigDecimal finalItemCostSum;

    private String shopKey;


    private String isBeginWithOne;
    
    private String headStr;
    
    private Integer waterCount;

    public String getTransferInfo() {
        return transferInfo;
    }

    public void setTransferInfo(String transferInfo) {
        this.transferInfo = transferInfo;
    }

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

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

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Double getItemCostsSum() {
        return itemCostsSum==null?0.0:itemCostsSum;
    }

    public void setItemCostsSum(Double itemCostsSum) {
        this.itemCostsSum = itemCostsSum;
    }

    public Double getDiscountCosts() {
        return discountCosts;
    }

    public void setDiscountCosts(Double discountCosts) {
        this.discountCosts = discountCosts;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getPrivateRoomCost() {
        return privateRoomCost;
    }

    public void setPrivateRoomCost(Double privateRoomCost) {
        this.privateRoomCost = privateRoomCost;
    }

    public Double getMinimumConsumption() {
        return minimumConsumption;
    }

    public void setMinimumConsumption(Double minimumConsumption) {
        this.minimumConsumption = minimumConsumption;
    }

    public Double getMinimumChargeComplete() {
        return minimumChargeComplete;
    }

    public void setMinimumChargeComplete(Double minimumChargeComplete) {
        this.minimumChargeComplete = minimumChargeComplete;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getClearingWaterId() {
        return clearingWaterId;
    }

    public void setClearingWaterId(Integer clearingWaterId) {
        this.clearingWaterId = clearingWaterId;
    }

    public String getFirstTime() {
        return (firstTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(firstTime):null;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Integer getSeatId2() {
        return seatId2;
    }

    public void setSeatId2(Integer seatId2) {
        this.seatId2 = seatId2;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public Integer getAllowNumber() {
        return allowNumber;
    }

    public void setAllowNumber(Integer allowNumber) {
        this.allowNumber = allowNumber;
    }

    public Integer getSeatState() {
        return seatState;
    }

    public void setSeatState(Integer seatState) {
        this.seatState = seatState;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public void setSeatLabel(String seatLabel) {
        this.seatLabel = seatLabel;
    }

    public Integer getOpenPos() {
        return openPos;
    }

    public void setOpenPos(Integer openPos) {
        this.openPos = openPos;
    }

    public Integer getWaiter() {
        return waiter;
    }

    public void setWaiter(Integer waiter) {
        this.waiter = waiter;
    }

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getOwNotes() {
        return owNotes;
    }

    public void setOwNotes(String owNotes) {
        this.owNotes = owNotes;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getClosedTime() {
    	return (closedTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(closedTime):null;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public Integer getTeamMainSeat() {
        return teamMainSeat;
    }

    public void setTeamMainSeat(Integer teamMainSeat) {
        this.teamMainSeat = teamMainSeat;
    }

	public Integer getOwState() {
		return OwState;
	}

	public void setOwState(Integer owState) {
		OwState = owState;
	}

	public Integer getIsTeam() {
		return isTeam;
	}

	public void setIsTeam(Integer isTeam) {
		this.isTeam = isTeam;
	}

    public String getTransferTarget() {
        return transferTarget;
    }

    public void setTransferTarget(String transferTarget) {
        this.transferTarget = transferTarget;
    }

    public String getJoinTeamNotes() {
        return joinTeamNotes;
    }

    public void setJoinTeamNotes(String joinTeamNotes) {
        this.joinTeamNotes = joinTeamNotes;
    }

    public String getJoinTeamTime() {
        return (joinTeamTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(joinTeamTime):null;
    }

    public void setJoinTeamTime(Date joinTeamTime) {
        this.joinTeamTime = joinTeamTime;
    }

    public Integer getSplitOpenWater() {
        return splitOpenWater;
    }

    public void setSplitOpenWater(Integer splitOpenWater) {
        this.splitOpenWater = splitOpenWater;
    }

    public String getSplitOpenWaterNum() {
        return splitOpenWaterNum;
    }

    public void setSplitOpenWaterNum(String splitOpenWaterNum) {
        this.splitOpenWaterNum = splitOpenWaterNum;
    }

    public String getSplitTime() {
        return (splitTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(splitTime):null;
    }

    public void setSplitTime(Date splitTime) {
        this.splitTime = splitTime;
    }

    public String getOpenBis() {
        return openBis;
    }

    public void setOpenBis(String openBis) {
        this.openBis = openBis;
    }

	public Integer getSeatAmount() {
		return seatAmount;
	}

	public void setSeatAmount(Integer seatAmount) {
		this.seatAmount = seatAmount;
	}

    public Integer getMarketingStaff() {
        return marketingStaff;
    }

    public void setMarketingStaff(Integer marketingStaff) {
        this.marketingStaff = marketingStaff;
    }

    public String getWaiterCode() {
        return waiterCode;
    }

    public void setWaiterCode(String waiterCode) {
        this.waiterCode = waiterCode;
    }

    public String getMarketingStaffCode() {
        return marketingStaffCode;
    }

    public void setMarketingStaffCode(String marketingStaffCode) {
        this.marketingStaffCode = marketingStaffCode;
    }

    public String getMarketingStaffName() {
        return marketingStaffName;
    }

    public void setMarketingStaffName(String marketingStaffName) {
        this.marketingStaffName = marketingStaffName;
    }

    public Integer getYjdPrintNum() {
        return yjdPrintNum;
    }

    public void setYjdPrintNum(Integer yjdPrintNum) {
        this.yjdPrintNum = yjdPrintNum;
    }

    public Integer getKydPrintNum() {
        return kydPrintNum;
    }

    public void setKydPrintNum(Integer kydPrintNum) {
        this.kydPrintNum = kydPrintNum;
    }

    public String getHandingsbillTime() {
        return (handingsbillTime != null)?(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(handingsbillTime)):null;
    }

    public void setHandingsbillTime(Date handingsbillTime) {
        this.handingsbillTime = handingsbillTime;
    }

    public String getOpenOperator() {
        return openOperator;
    }

    public void setOpenOperator(String openOperator) {
        this.openOperator = openOperator;
    }

    public String getOpenOperatorName() {
        return openOperatorName;
    }

    public void setOpenOperatorName(String openOperatorName) {
        this.openOperatorName = openOperatorName;
    }

    public Integer getIsIncreasingItem() {
        return isIncreasingItem;
    }

    public void setIsIncreasingItem(Integer isIncreasingItem) {
        this.isIncreasingItem = isIncreasingItem;
    }

    public List<DgOwConsumerDetails> getItemFileInfos() {
        return itemFileInfos;
    }

    public void setItemFileInfos(List<DgOwConsumerDetails> itemFileInfos) {
        this.itemFileInfos = itemFileInfos;
    }

    public Map<String, Object> getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(Map<String, Object> memberInfo) {
        this.memberInfo = memberInfo;
    }

    public List<Map> getMemberCardList() {
        return memberCardList;
    }

    public void setMemberCardList(List<Map> memberCardList) {
        this.memberCardList = memberCardList;
    }

    public Integer getPrivateRoomType() {
        return privateRoomType;
    }

    public void setPrivateRoomType(Integer privateRoomType) {
        this.privateRoomType = privateRoomType;
    }

    public List<DgBigItemTypeInfoList> getItemFileByBigTypes() {
        return itemFileByBigTypes;
    }

    public void setItemFileByBigTypes(List<DgBigItemTypeInfoList> itemFileByBigTypes) {
        this.itemFileByBigTypes = itemFileByBigTypes;
    }

    public Double getModifyServiceCharge() {
        return modifyServiceCharge;
    }

    public void setModifyServiceCharge(Double modifyServiceCharge) {
        this.modifyServiceCharge = modifyServiceCharge;
    }

    public Integer getFreeServceCharge() {
        return freeServceCharge;
    }

    public void setFreeServceCharge(Integer freeServceCharge) {
        this.freeServceCharge = freeServceCharge;
    }

    public Integer getFreeMinSpend() {
        return freeMinSpend;
    }

    public void setFreeMinSpend(Integer freeMinSpend) {
        this.freeMinSpend = freeMinSpend;
    }

    public Integer getFreePrivateRoom() {
        return freePrivateRoom;
    }

    public void setFreePrivateRoom(Integer freePrivateRoom) {
        this.freePrivateRoom = freePrivateRoom;
    }

    public Double getModifyPrivateRoom() {
        return modifyPrivateRoom;
    }

    public void setModifyPrivateRoom(Double modifyPrivateRoom) {
        this.modifyPrivateRoom = modifyPrivateRoom;
    }

    public Double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Double getPxdzZdxfbq() {
        return pxdzZdxfbq;
    }

    public void setPxdzZdxfbq(Double pxdzZdxfbq) {
        this.pxdzZdxfbq = pxdzZdxfbq;
    }

    public Double getZyhdZdxfbq() {
        return zyhdZdxfbq;
    }

    public void setZyhdZdxfbq(Double zyhdZdxfbq) {
        this.zyhdZdxfbq = zyhdZdxfbq;
    }

    public Double getHydzZdxfbq() {
        return hydzZdxfbq;
    }

    public void setHydzZdxfbq(Double hydzZdxfbq) {
        this.hydzZdxfbq = hydzZdxfbq;
    }

    public Double getZyhdItemSubtotal() {
        return zyhdItemSubtotal;
    }

    public void setZyhdItemSubtotal(Double zyhdItemSubtotal) {
        this.zyhdItemSubtotal = zyhdItemSubtotal;
    }

    public Double getPxdzItemSubtotal() {
        return pxdzItemSubtotal;
    }

    public void setPxdzItemSubtotal(Double pxdzItemSubtotal) {
        this.pxdzItemSubtotal = pxdzItemSubtotal;
    }

    public Double getHydzItemSubtotal() {
        return hydzItemSubtotal;
    }

    public void setHydzItemSubtotal(Double hydzItemSubtotal) {
        this.hydzItemSubtotal = hydzItemSubtotal;
    }

    public Double getZyhdSubtotal() {
        return zyhdSubtotal;
    }

    public void setZyhdSubtotal(Double zyhdSubtotal) {
        this.zyhdSubtotal = zyhdSubtotal;
    }

    public Double getPxdzSubtotal() {
        return pxdzSubtotal;
    }

    public void setPxdzSubtotal(Double pxdzSubtotal) {
        this.pxdzSubtotal = pxdzSubtotal;
    }

    public Double getHydzSubtotal() {
        return hydzSubtotal;
    }

    public void setHydzSubtotal(Double hydzSubtotal) {
        this.hydzSubtotal = hydzSubtotal;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getCrName() {
        return crName;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public BigDecimal getFinalItemCostSum() {
        return finalItemCostSum;
    }

    public void setFinalItemCostSum(BigDecimal finalItemCostSum) {
        this.finalItemCostSum = finalItemCostSum;
    }

	public Integer getYdId() {
		return ydId;
	}

	public void setYdId(Integer ydId) {
		this.ydId = ydId;
	}

    public Boolean getFreePR() {
        return isFreePR;
    }

    public void setFreePR(Boolean freePR) {
        isFreePR = freePR;
    }

    public String getSplitTime2() {
        return splitTime2;
    }

    public void setSplitTime2(String splitTime2) {
        this.splitTime2 = splitTime2;
    }

	public String getTableno() {
		return tableno;
	}

	public void setTableno(String tableno) {
		this.tableno = tableno;
	}

	public Boolean getIsFreePR() {
		return isFreePR;
	}

	public void setIsFreePR(Boolean isFreePR) {
		this.isFreePR = isFreePR;
	}

	public String getIsBeginWithOne() {
		return isBeginWithOne;
	}

	public void setIsBeginWithOne(String isBeginWithOne) {
		this.isBeginWithOne = isBeginWithOne;
	}

	public String getHeadStr() {
		return headStr;
	}

	public void setHeadStr(String headStr) {
		this.headStr = headStr;
	}

	public Integer getWaterCount() {
		return waterCount;
	}

	public void setWaterCount(Integer waterCount) {
		this.waterCount = waterCount;
	}
    
    
}