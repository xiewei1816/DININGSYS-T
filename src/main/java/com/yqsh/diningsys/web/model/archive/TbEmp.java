package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;


/**
 * 员工实体
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:28:03
 */
public class TbEmp extends BasePojo{

	private Integer id;

    private String empCode;    //员工编号

    private String empName;    //员工姓名

    private String empOrganization;  //所属机构
 
    private String empDepartment;    //所属部门
     
    private String empDuties;  //职务
    
    private String empDob;   //出生日期
    
    private String empCardno;  //身份证号

    private String empSex;  //性别
    
    private String isMarry;  //婚姻状况
    
    private String empCardid;  //员工卡ID
    
    private String sqhNo1;  //授权号1
    
    private String sqhNo2;  //授权号2
    
    private String isOrguserFlag;  //集团用户标志
    
    private String isManagerFlag;  //管理员标志

    private String isStartFlag;  //启动标志
    
    private String natives; //籍贯
    
    private String place;   //户籍地
    
    private String phone;   //固定电话
    
    private String email;   //邮箱
    
    private String height;   //身高
    
    private String nation;   //民族
    
    private String mobile;   //移动电话
    
    private String address;  //家庭住址
    
    private String photo;   //头像
    
    private String is_del;  //是否删除 0-未删除 1-已删除
    
    private String isDel;
    
    private Date createTime; //创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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
		this.empDob = empDob;
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

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getCreateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(createTime != null)
            return simpleDateFormat.format(createTime);
        else return null;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}