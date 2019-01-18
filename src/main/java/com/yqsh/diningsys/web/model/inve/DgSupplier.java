package com.yqsh.diningsys.web.model.inve;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 供应商
 * @author jianglei
 * 日期 2016年10月17日 上午9:14:42
 *
 */
@SuppressWarnings("serial")
public class DgSupplier extends BasePojo implements Serializable{
	/**
	 * 供应商编码流水位数:值是多少就表示多少位
	 */
	public static final int SUPERNO_DIGITS=5;
	private String id;            //编号，存入uuid
	private String superNo;       //供应商编码，规则S+N位流水按照顺序，如:S00001,S00002,...
	private String supName;       //供应商名称
	private String contactName;   //联系人
	private String state;         //状态 0表示正常，1表示删除
	private String phone;         //联系电话
	private String fax;           //传真
	private String email;         //电子邮件
	private String address;       //地址
	private String level;         //等级
	private String remark;        //备注
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSuperNo() {
		return superNo;
	}

	public void setSuperNo(String superNo) {
		this.superNo = superNo;
	}

	public DgSupplier() {
		super();
	}
}
