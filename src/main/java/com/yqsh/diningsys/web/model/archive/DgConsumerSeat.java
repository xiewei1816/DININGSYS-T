package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * 客位
 *
 * @author ls001
 */
public class DgConsumerSeat extends BasePojo {

    /**
     *
     */
    private static final long serialVersionUID = 6893562944460800758L;

    private Integer id;
    private Integer allowNumberId;                        //容纳人数标准
    private String number;                                //客位编号
    private String name;                                //客位名称
    private String allName;                                //全名称
    private String seatType;                            //客位类型
	private Integer allowNumber;                        //容纳人数
    private Integer defaultState;                        //结算后默认状态
    private Integer consArea;                            //所属消费区域
    private DgConsumptionArea area;                        //所属消费区域对象
    private String mnemonic;                            //客位助记符
    private String createTime;                            //创建时间
    private String explains;                            //说明
    private Integer disabledSeat;                        //客位停用（0否 1是）
    private Integer insetRetentionRoom;                    //内部留房（0否 1是）
    private Integer immediateSettlement = 0;                //加单后立即结算（0否 1是）
    private Integer notStatisticsOvertaiwan;            //不统计翻台率（0否 1是）
    private Integer emptyGuest = 0;                            //虚客位（0否 1是）
    private Integer customerRetention;                    //留客（用于到客等候排队）（0否 1是）
    private String createUserid;                        //创建数据用户
    private SysUser createUser;                            //创建用户对象
    private Integer delFlag = 0;                            //删除标记（0未删除 1已删除）
    private String conditions;                            //多查询条件
    private String crStartTime;                            //创建开始时间（查询条件）
    private String crEndTime;                            //创建结束时间（查询条件）
    private String checkName;
    private Integer seatState;                           //客位状态
    private Date lastOpenTime;							 //最后开台时间
    
    /**
     * VIP房间标识，属性0|1
     */
    private Integer vipIdentify;

    /**
     * 实时人数
     */
    private Integer realTimePeopleCount;

    private String seatLabel;

    private String areaName;

    private Integer waiterId;

    private Integer qssc;
    
    private String uuidKey;

    private String shopKey;

    /*2017年7月27日17:10:18*/
    /**
     * 客座上显示团队信息
     */
    private List<String> teamInfo;

    /**
     * 客座上显示转账信息
     */
    private List<String> transferInfo;

    /**
     * 临时标记，记录是转账还是只是团队
     */
    private Integer owFlag;

    /*END*/
    
    private DgReserveManager drm;

    public Integer getOwFlag() {
        return owFlag;
    }

    public void setOwFlag(Integer owFlag) {
        this.owFlag = owFlag;
    }

    public List<String> getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(List<String> teamInfo) {
        this.teamInfo = teamInfo;
    }

    public List<String> getTransferInfo() {
        return transferInfo;
    }

    public void setTransferInfo(List<String> transferInfo) {
        this.transferInfo = transferInfo;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public void setSeatLabel(String seatLabel) {
        this.seatLabel = seatLabel;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getRealTimePeopleCount() {
        return realTimePeopleCount;
    }

    public void setRealTimePeopleCount(Integer realTimePeopleCount) {
        this.realTimePeopleCount = realTimePeopleCount;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Integer getId() {
        return id;
    }
    
    public Integer getAllowNumberId() {
		return allowNumberId;
	}

	public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getAllName() {
        return allName;
    }

    public String getSeatType() {
        return seatType;
    }

    public Integer getAllowNumber() {
        return allowNumber;
    }

    public Integer getDefaultState() {
        return defaultState;
    }

    public Integer getConsArea() {
        return consArea;
    }

    public DgConsumptionArea getArea() {
        return area;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getExplains() {
        return explains;
    }

    public Integer getDisabledSeat() {
        return disabledSeat;
    }

    public Integer getInsetRetentionRoom() {
        return insetRetentionRoom;
    }

    public Integer getImmediateSettlement() {
        return immediateSettlement;
    }

    public Integer getNotStatisticsOvertaiwan() {
        return notStatisticsOvertaiwan;
    }

    public Integer getEmptyGuest() {
        return emptyGuest;
    }

    public Integer getCustomerRetention() {
        return customerRetention;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public SysUser getCreateUser() {
        return createUser;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public String getConditions() {
        return conditions;
    }

    public String getCrStartTime() {
        return crStartTime;
    }

    public String getCrEndTime() {
        return crEndTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public void setAllowNumberId(Integer allowNumberId) {
		this.allowNumberId = allowNumberId;
	}

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllName(String allName) {
        this.allName = allName;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public void setAllowNumber(Integer allowNumber) {
        this.allowNumber = allowNumber;
    }

    public void setDefaultState(Integer defaultState) {
        this.defaultState = defaultState;
    }

    public void setConsArea(Integer consArea) {
        this.consArea = consArea;
    }

    public void setArea(DgConsumptionArea area) {
        this.area = area;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public void setDisabledSeat(Integer disabledSeat) {
        this.disabledSeat = disabledSeat;
    }

    public void setInsetRetentionRoom(Integer insetRetentionRoom) {
        this.insetRetentionRoom = insetRetentionRoom;
    }

    public void setImmediateSettlement(Integer immediateSettlement) {
        this.immediateSettlement = immediateSettlement;
    }

    public void setNotStatisticsOvertaiwan(Integer notStatisticsOvertaiwan) {
        this.notStatisticsOvertaiwan = notStatisticsOvertaiwan;
    }

    public void setEmptyGuest(Integer emptyGuest) {
        this.emptyGuest = emptyGuest;
    }

    public void setCustomerRetention(Integer customerRetention) {
        this.customerRetention = customerRetention;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public void setCreateUser(SysUser createUser) {
        this.createUser = createUser;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public void setCrStartTime(String crStartTime) {
        this.crStartTime = crStartTime;
    }

    public void setCrEndTime(String crEndTime) {
        this.crEndTime = crEndTime;
    }

	public Integer getSeatState() {
		return seatState;
	}

	public void setSeatState(Integer seatState) {
		this.seatState = seatState;
	}

    public Integer getVipIdentify() {
        return vipIdentify;
    }

    public void setVipIdentify(Integer vipIdentify) {
        this.vipIdentify = vipIdentify;
    }

	public String  getLastOpenTime() {
		 return (lastOpenTime != null)?(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastOpenTime)):null;
	}

	public void setLastOpenTime(Date lastOpenTime) {
		this.lastOpenTime = lastOpenTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public Integer getQssc() {
        return qssc;
    }

    public void setQssc(Integer qssc) {
        this.qssc = qssc;
    }

	public String getUuidKey() {
		return uuidKey;
	}

	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

	public DgReserveManager getDrm() {
		return drm;
	}

	public void setDrm(DgReserveManager drm) {
		this.drm = drm;
	}
    
    
    
}
