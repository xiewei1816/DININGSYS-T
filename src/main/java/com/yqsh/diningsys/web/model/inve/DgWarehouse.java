package com.yqsh.diningsys.web.model.inve;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 仓库管理
 * @author jianglei
 * 日期 2016年10月18日 下午1:55:45
 *
 */
@SuppressWarnings("serial")
public class DgWarehouse extends BasePojo implements Serializable{
	/**
	 * 编码流水位数:值是多少就表示多少位
	 */
	public static final int WARENO_DIGITS=4;
	private String id;         //编号
	private String wareNo;     //编码
	private String wareName;   //仓库名称
	private String manageName; //管理人
	private String state;      //状态 0表示正常，1表示删除
	private String remark;     //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getManageName() {
		return manageName;
	}
	public void setManageName(String manageName) {
		this.manageName = manageName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWareNo() {
		return wareNo;
	}
	public void setWareNo(String wareNo) {
		this.wareNo = wareNo;
	}
}
