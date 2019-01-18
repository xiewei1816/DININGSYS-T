package com.yqsh.diningsys.web.model.inve;
/**
 * 库存管理
 * @author jianglei
 * 日期 2016年10月19日 下午4:15:27
 *
 */
@SuppressWarnings("serial")
public class DgInventory extends DgBaseInventory{
	private String id;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public DgInventory() {
		super();
	}
}
