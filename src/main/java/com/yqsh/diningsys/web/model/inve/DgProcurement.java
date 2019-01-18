package com.yqsh.diningsys.web.model.inve;
/**
 * 采购管理
 * @author jianglei
 * 日期 2016年10月20日 下午1:55:11
 *
 */
@SuppressWarnings("serial")
public class DgProcurement extends DgBaseInventory{
	/**
	 * 流水位数:值是多少就表示多少位
	 */
	public static final int SERIA_DIGITS=4;
	/**
	 * 采购类型:0表示采购入库
	 */
	public static final String PROCTYPE_INTO="0";
	/**
	 * 采购类型:1表示采购退货
	 */
	public static final String PROCTYPE_OUT="1";
	private String id;         //编号
	private String procType;   //采购类型，0:表示采购入库，1表示采购退货
	private String inveId;     //库存编号
	private String remark;     //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getInveId() {
		return inveId;
	}
	public void setInveId(String inveId) {
		this.inveId = inveId;
	}
	public DgProcurement() {
		super();
	}
}
