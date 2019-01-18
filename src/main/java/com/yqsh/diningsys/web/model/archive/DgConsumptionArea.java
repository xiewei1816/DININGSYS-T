package com.yqsh.diningsys.web.model.archive;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * 消费区域
 * @author ls001
 *
 */
public class DgConsumptionArea extends BasePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357610546711527174L;
	
	private Integer id;
	private String number; 				//消费区域编号
	private String name;				//消费区域名称
	private String mnemonic;			//消费区域助记符
	private String createTime;			//创建时间
	private String explains;			//说明
	private String createUserid;		//创建数据用户
	private SysUser createUser;			//创建用户对象
	private Integer delFlag;			//删除标记（0未删除 1已删除）
	private String conditions; 			//多查询条件
	private String crStartTime;			//创建开始时间（查询条件）
	private String crEndTime;			//创建结束时间（查询条件）
	private String checkName;
	
	private String uuidKey;
	private String shopKey;
	private String parentId; //树需要
	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getCreateUserid() {
		return createUserid;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getCrStartTime() {
		return crStartTime;
	}

	public String getCrEndTime() {
		return crEndTime;
	}

	public void setCrStartTime(String crStartTime) {
		this.crStartTime = crStartTime;
	}

	public void setCrEndTime(String crEndTime) {
		this.crEndTime = crEndTime;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getMnemonic() {
		return mnemonic;
	}


	public SysUser getCreateUser() {
		return createUser;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}


	public void setCreateUser(SysUser createUser) {
		this.createUser = createUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	
}
