package com.yqsh.diningsys.web.model.inve;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 物品类型管理
 * @author jianglei
 * 日期 2016年10月18日 下午5:20:11
 *
 */
@SuppressWarnings("serial")
public class DgItemType extends BasePojo implements Serializable{
	private String id;             //编号
	private String itemTypeNo;     //编码
	private String itemTypeName;   //物品类型名称
	private String state;          //状态 0表示正常，1表示删除
	private String remark;         //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
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
	
	public String getItemTypeNo() {
		return itemTypeNo;
	}
	public void setItemTypeNo(String itemTypeNo) {
		this.itemTypeNo = itemTypeNo;
	}
	public DgItemType() {
		super();
	}
}
