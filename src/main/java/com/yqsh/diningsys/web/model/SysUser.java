package com.yqsh.diningsys.web.model;

import com.yqsh.diningsys.core.util.BasePojo;

import java.io.Serializable;
import java.util.Date;


public class SysUser extends BasePojo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6489058702146561647L;

    private Integer id;

    private String password;

    private String state;

    private String empCode;

    private String empOrganization;

    private String empDepartment;

    private String empDuties;

    private String empDob;   //出生日期

    private String empCardno;

    private String empSex;

    private String isMarry;

    private String empCardid;

    private String sqhNo1;

    private String sqhNo2;

    private String isOrguserFlag;

    private String isManagerFlag;

    private String isStartFlag;

    private String natives;

    private String place;

    private String phone;

    private String email;

    private String height;

    private String nation;

    private String mobile;

    private String address;

    private String photo;

    private String isDel;

    private Date createTime;

    private String empName;

    private String empDepartmentName;

    private String empOrganizationName;

    private String shopKey;

    private String uuidKey;

    //系统权限使用->是否选中
    private String selected;

    //开单使用，默认是否为服务员
    private Boolean openBillDefault = false;

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public SysUser() {

    }

    public SysUser(String empCode, String password) {
        this.empCode = empCode;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpDuties() {
        return empDuties;
    }

    public void setEmpDuties(String empDuties) {
        this.empDuties = empDuties;
    }
	
	public String getEmpDob() {
		 return empDob;
	}

	public void setEmpDob(String empDob) {
		this.empDob = empDob == "" ? null : empDob.trim();
	}

    public String getEmpCardno() {
        return empCardno;
    }

    public void setEmpCardno(String empCardno) {
        this.empCardno = empCardno;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public String getIsMarry() {
        return isMarry;
    }

    public void setIsMarry(String isMarry) {
        this.isMarry = isMarry;
    }

    public String getEmpCardid() {
        return empCardid;
    }

    public void setEmpCardid(String empCardid) {
        this.empCardid = empCardid;
    }

    public String getSqhNo1() {
        return sqhNo1;
    }

    public void setSqhNo1(String sqhNo1) {
        this.sqhNo1 = sqhNo1;
    }

    public String getSqhNo2() {
        return sqhNo2;
    }

    public void setSqhNo2(String sqhNo2) {
        this.sqhNo2 = sqhNo2;
    }

    public String getIsOrguserFlag() {
        return isOrguserFlag;
    }

    public void setIsOrguserFlag(String isOrguserFlag) {
        this.isOrguserFlag = isOrguserFlag;
    }

    public String getIsManagerFlag() {
        return isManagerFlag;
    }

    public void setIsManagerFlag(String isManagerFlag) {
        this.isManagerFlag = isManagerFlag;
    }

    public String getIsStartFlag() {
        return isStartFlag;
    }

    public void setIsStartFlag(String isStartFlag) {
        this.isStartFlag = isStartFlag;
    }

    public String getNatives() {
        return natives;
    }

    public void setNatives(String natives) {
        this.natives = natives;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpOrganization() {
        return empOrganization;
    }

    public void setEmpOrganization(String empOrganization) {
        this.empOrganization = empOrganization;
    }

    public String getEmpDepartment() {
        return empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    public String getEmpDepartmentName() {
        return empDepartmentName;
    }

    public void setEmpDepartmentName(String empDepartmentName) {
        this.empDepartmentName = empDepartmentName;
    }

    public String getEmpOrganizationName() {
        return empOrganizationName;
    }

    public void setEmpOrganizationName(String empOrganizationName) {
        this.empOrganizationName = empOrganizationName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public Boolean getOpenBillDefault() {
        return openBillDefault;
    }

    public void setOpenBillDefault(Boolean openBillDefault) {
        this.openBillDefault = openBillDefault;
    }
}