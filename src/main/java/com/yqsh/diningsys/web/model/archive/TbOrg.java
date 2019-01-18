package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;


/**
 * 机构管理
* @author xiewei
* @version 创建时间：2016年10月10日 下午2:41:27
 */
public class TbOrg extends BasePojo{

	private Integer id;

    private String orgCode;    //机构代码

    private String orgName;    //机构名称

    private String orgSjm;     //速记码 
    
    private String orgKdsj;    //开店日期
    
    private String orgJmgh;  //加密狗号

    private String orgPhone;  //电话
    
    private String orgGlms;  //管理模式
    
    private String orgArea;  //区域
    
    private String orgBrand;  //品牌
    
    private String franchisees;  //加盟商
    
    private String maxCustomer;  //最大容纳客数
    
    private String address;  //地址
    
    private String remark;  //备注
    
    private String isOwnFlag; //本店标志
    
    private String isStartFlag; //启用标志
    
    private String isNewstoreFlag; //新店标志
    
    private String isLineFlag; //排队标志
    
    private String rdc; //所属RDC
    
    private String province; //省
    
    private String city; //市
    
    private String region; //区
    
    private String road; //路
    
    private String area; //面积
    
    private String rdcDistance; //距RDC距离
    
    private String diyFields1; //自定义字段1
    
    private String diyFields2; //自定义字段2
    
    private String diyFields3; //自定义字段3
    
    private String diyFields4; //自定义字段4
    
    private String diyFields5; //自定义字段5
    
    private Date createTime; //创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgSjm() {
		return orgSjm;
	}

	public void setOrgSjm(String orgSjm) {
		this.orgSjm = orgSjm;
	}

	public String getOrgKdsj() {
		return orgKdsj;
	}

	public void setOrgKdsj(String orgKdsj) {
		this.orgKdsj = orgKdsj;
	}

	public String getOrgJmgh() {
		return orgJmgh;
	}

	public void setOrgJmgh(String orgJmgh) {
		this.orgJmgh = orgJmgh;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgGlms() {
		return orgGlms;
	}

	public void setOrgGlms(String orgGlms) {
		this.orgGlms = orgGlms;
	}

	public String getOrgArea() {
		return orgArea;
	}

	public void setOrgArea(String orgArea) {
		this.orgArea = orgArea;
	}

	public String getOrgBrand() {
		return orgBrand;
	}

	public void setOrgBrand(String orgBrand) {
		this.orgBrand = orgBrand;
	}

	public String getFranchisees() {
		return franchisees;
	}

	public void setFranchisees(String franchisees) {
		this.franchisees = franchisees;
	}

	public String getMaxCustomer() {
		return maxCustomer;
	}

	public void setMaxCustomer(String maxCustomer) {
		this.maxCustomer = maxCustomer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsOwnFlag() {
		return isOwnFlag;
	}

	public void setIsOwnFlag(String isOwnFlag) {
		this.isOwnFlag = isOwnFlag;
	}

	public String getIsStartFlag() {
		return isStartFlag;
	}

	public void setIsStartFlag(String isStartFlag) {
		this.isStartFlag = isStartFlag;
	}

	public String getIsNewstoreFlag() {
		return isNewstoreFlag;
	}

	public void setIsNewstoreFlag(String isNewstoreFlag) {
		this.isNewstoreFlag = isNewstoreFlag;
	}
	
	public String getIsLineFlag() {
		return isLineFlag;
	}

	public void setIsLineFlag(String isLineFlag) {
		this.isLineFlag = isLineFlag;
	}

	public String getRdc() {
		return rdc;
	}

	public void setRdc(String rdc) {
		this.rdc = rdc;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRdcDistance() {
		return rdcDistance;
	}

	public void setRdcDistance(String rdcDistance) {
		this.rdcDistance = rdcDistance;
	}

	public String getDiyFields1() {
		return diyFields1;
	}

	public void setDiyFields1(String diyFields1) {
		this.diyFields1 = diyFields1;
	}

	public String getDiyFields2() {
		return diyFields2;
	}

	public void setDiyFields2(String diyFields2) {
		this.diyFields2 = diyFields2;
	}

	public String getDiyFields3() {
		return diyFields3;
	}

	public void setDiyFields3(String diyFields3) {
		this.diyFields3 = diyFields3;
	}

	public String getDiyFields4() {
		return diyFields4;
	}

	public void setDiyFields4(String diyFields4) {
		this.diyFields4 = diyFields4;
	}

	public String getDiyFields5() {
		return diyFields5;
	}

	public void setDiyFields5(String diyFields5) {
		this.diyFields5 = diyFields5;
	}

	public String getCreateTime(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(createTime != null)
            return simpleDateFormat.format(createTime);
        else 
        	return null;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}