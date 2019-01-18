package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;

/**
 * 特约商户打折方案
 * @author ls001
 * table 'dg_specially_business'
 *
 */
public class DgSpeciallyBusiness extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8787000053667402353L;
	private Integer id;
	private String name;								//方案名称
	private String businessId;							//商户id值
	private DgPublicCode business;						//商户实体
	private Double discountRate;						//打折比例
	private Integer isEditRate;							//前台是否可以修改优惠比例(0不可以 1可以)
	private String createTime;							//创建时间
	private String createUserid;						//创建数据用户
	private SysUser createUser;							//创建用户对象
	private Integer delFlag;							//删除标记（0未删除 1已删除）
	private String conditions; 							//多查询条件
	private String crStartTime;							//创建开始时间（查询条件）
	private String crEndTime;							//创建结束时间（查询条件）
	private String checkName;
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getBusinessId() {
		return businessId;
	}
	public DgPublicCode getBusiness() {
		return business;
	}
	public Double getDiscountRate() {
		return discountRate;
	}
	public Integer getIsEditRate() {
		return isEditRate;
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
	public String getCheckName() {
		return checkName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public void setBusiness(DgPublicCode business) {
		this.business = business;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	public void setIsEditRate(Integer isEditRate) {
		this.isEditRate = isEditRate;
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
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
}
