package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * 非会员信息
 * @author ls001
 * table 'dg_non_member'
 *
 */
public class DgNonMember extends BasePojo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2400607344118881594L;
	private Integer id;
	private String number; 				//账户编号
	private String name;				//账户名称
	private String mnemonic;			//助记符
	private Integer accountType;		//账户类型（1.个人,2.公司）
	private Integer empId;				//客户经理
	private SysUser emp;				//客户经理实体
	private Double balance;				//账户余额
	private Integer repaymentPeriod;    //还款周期
	private Double creditLimit;			//信用额度
	private String contacts;			//联系人
	private String phone;				//电话
	private String address;				//地址
	private String explains;			//说明
	private Integer isDisable;			//是否停用（0.否,1.是）
	private String createTime;			//创建时间
	private String createUserid;		//创建数据用户
	private SysUser createUser;			//创建用户对象
	private Integer delFlag;			//删除标记（0未删除 1已删除）
	private String conditions; 			//多查询条件
	private String crStartTime;			//创建开始时间（查询条件）
	private String crEndTime;			//创建结束时间（查询条件）
	private String checkName;
	
	public SysUser getEmp() {
		return emp;
	}
	public void setEmp(SysUser emp) {
		this.emp = emp;
	}
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
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
	public String getMnemonic() {
		return mnemonic;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public Integer getEmpId() {
		return empId;
	}
	public Double getBalance() {
		return balance;
	}
	public Integer getRepaymentPeriod() {
		return repaymentPeriod;
	}
	public Double getCreditLimit() {
		return creditLimit;
	}
	public String getContacts() {
		return contacts;
	}
	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
	public String getExplains() {
		return explains;
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
	public void setNumber(String number) {
		this.number = number;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public void setRepaymentPeriod(Integer repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setExplains(String explains) {
		this.explains = explains;
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
