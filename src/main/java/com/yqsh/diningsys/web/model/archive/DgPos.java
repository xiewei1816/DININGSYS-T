package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * pos档案
 * @author ls001
 *
 */
public class DgPos extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1782865153891059701L;
	
	private Integer id;
	private String number; 								//POS编号
	private String name;								//名称
	private String ipArea;								//Ip地址
	private String mnemonic;							//POS助记符
	private String consumerAreas;						//所在的消费区域
	private String organization;						//所属组织结构
	private String createTime;							//创建时间
	private String createUserid;						//创建数据用户
	private SysUser createUser;							//创建用户对象
	private String delFlag;							    //删除标记（0未删除 1已删除）
	private String conditions; 							//多查询条件
	private String crStartTime;							//创建开始时间（查询条件）
	private String crEndTime;							//创建结束时间（查询条件）
	private String checkName;

	/**
	 * 该POS是否能结班
	 */
	private Integer canJb;

	private String uuidKey;
	private String shopKey;

	public Integer getCanJb() {
		return canJb;
	}

	public void setCanJb(Integer canJb) {
		this.canJb = canJb;
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
	public Integer getId() {
		return id;
	}
	public String getNumber() {
		return number;
	}
	public String getName() {
		return name;
	}
	public String getIpArea() {
		return ipArea;
	}
	public String getMnemonic() {
		return mnemonic;
	}
	public String getConsumerAreas() {
		return consumerAreas;
	}
	public String getOrganization() {
		return organization;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public SysUser getCreateUser() {
		return createUser;
	}
	public String getDelFlag() {
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
	public void setNumber(String number) {
		this.number = number;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIpArea(String ipArea) {
		this.ipArea = ipArea;
	}
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	public void setConsumerAreas(String consumerAreas) {
		this.consumerAreas = consumerAreas;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public void setCreateUser(SysUser createUser) {
		this.createUser = createUser;
	}
	public void setDelFlag(String delFlag) {
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
	public String getShopKey() {
		return shopKey;
	}
	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}	
}
