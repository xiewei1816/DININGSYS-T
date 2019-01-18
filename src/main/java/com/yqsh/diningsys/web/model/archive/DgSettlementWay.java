package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * 结算方式
 *
 * @author ls001
 */
public class DgSettlementWay extends BasePojo {

    /**
     *
     */
    private static final long serialVersionUID = 8371704828689114861L;
    private Integer id;
    private String number;                                //结算方式编号
    private String name;                                //结算方式名称
    private String type;                                //结算方式类型
    private DgSettlementWayType dwType;                    //结算方式类型对象
    private Double exchangeRate;                        //汇率
    private String shortcutKey;                            //快捷键
    private Double rollFaceValue;                        //巻面值
    private String createTime;                            //创建时间
    private String explains;                            //说明
    private Double actualIncomeRatio;                    //计入实际收入比例
    private Double notActualIncomeRatio;                //不计入实际收入比例
    private Integer isDisabled;                            //是否停用（1是，0否）
    private Integer isCommon;                            //是否常用（1是，0否）
    private Integer isKeepSystem;                        //是否系统保留（1是，0否）
    private Integer isMustInformation;                    //是否在结算时必须输入附加信息（1是，0否）
    private Integer isAloneUse;                            //是否单独使用，不能与其他计算方式同时使用（1是，0否）
    private Integer isCurrency;                            //是否本位币（1是，0否）
    private Integer isAllowChange;                        //是否允许找零（1是，0否）
    private Integer delFlag;                            //删除标记（0未删除 1已删除）
    private String createUserid;                        //创建数据用户
    private SysUser createUser;                            //创建用户对象
    private String conditions;                            //多查询条件
    private String crStartTime;                            //创建开始时间（查询条件）
    private String crEndTime;                            //创建结束时间（查询条件）
    private String checkName;                            //检查名称时使用

    private String uuidKey;
    private String shopKey;

    //结算方式相关联的品项抵扣设置 2017年10月17日09:50:31 by zhshuo
    private Integer wayId;

    private String itemIds;

    private Integer qyxdksz;

    private String qyxdkszName;

    private Integer dklx;

    private String dklxName;

    private String typeName;

    private String joinId;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
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

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public void setCrStartTime(String crStartTime) {
        this.crStartTime = crStartTime;
    }

    public void setCrEndTime(String crEndTime) {
        this.crEndTime = crEndTime;
    }

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public DgSettlementWayType getDwType() {
        return dwType;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public String getShortcutKey() {
        return shortcutKey;
    }

    public Double getRollFaceValue() {
        return rollFaceValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getExplains() {
        return explains;
    }

    public Double getActualIncomeRatio() {
        return actualIncomeRatio;
    }

    public Double getNotActualIncomeRatio() {
        return notActualIncomeRatio;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public Integer getIsCommon() {
        return isCommon;
    }

    public Integer getIsKeepSystem() {
        return isKeepSystem;
    }

    public Integer getIsMustInformation() {
        return isMustInformation;
    }

    public Integer getIsAloneUse() {
        return isAloneUse;
    }

    public Integer getIsCurrency() {
        return isCurrency;
    }

    public Integer getIsAllowChange() {
        return isAllowChange;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public SysUser getCreateUser() {
        return createUser;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDwType(DgSettlementWayType dwType) {
        this.dwType = dwType;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setShortcutKey(String shortcutKey) {
        this.shortcutKey = shortcutKey;
    }

    public void setRollFaceValue(Double rollFaceValue) {
        this.rollFaceValue = rollFaceValue;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public void setActualIncomeRatio(Double actualIncomeRatio) {
        this.actualIncomeRatio = actualIncomeRatio;
    }

    public void setNotActualIncomeRatio(Double notActualIncomeRatio) {
        this.notActualIncomeRatio = notActualIncomeRatio;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setIsCommon(Integer isCommon) {
        this.isCommon = isCommon;
    }

    public void setIsKeepSystem(Integer isKeepSystem) {
        this.isKeepSystem = isKeepSystem;
    }

    public void setIsMustInformation(Integer isMustInformation) {
        this.isMustInformation = isMustInformation;
    }

    public void setIsAloneUse(Integer isAloneUse) {
        this.isAloneUse = isAloneUse;
    }

    public void setIsCurrency(Integer isCurrency) {
        this.isCurrency = isCurrency;
    }

    public void setIsAllowChange(Integer isAllowChange) {
        this.isAllowChange = isAllowChange;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public void setCreateUser(SysUser createUser) {
        this.createUser = createUser;
    }

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

    public Integer getWayId() {
        return wayId;
    }

    public void setWayId(Integer wayId) {
        this.wayId = wayId;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public Integer getQyxdksz() {
        return qyxdksz;
    }

    public void setQyxdksz(Integer qyxdksz) {
        this.qyxdksz = qyxdksz;
    }

    public Integer getDklx() {
        return dklx;
    }

    public void setDklx(Integer dklx) {
        this.dklx = dklx;
    }

    public String getQyxdkszName() {
        if(qyxdksz == null)return null;
        return qyxdksz==1?"无限制":(qyxdksz==2?"品项大类":(qyxdksz==3?"品项小类":qyxdksz==4?"具体品项":"无"));
    }

    public void setQyxdkszName(String qyxdkszName) {
        this.qyxdkszName = qyxdkszName;
    }

    public String getDklxName() {
        if(dklx == null)return null;
        return dklx==1?"固定金额":dklx==2?"百分比折扣":"无";
    }

    public void setDklxName(String dklxName) {
        this.dklxName = dklxName;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }
}
