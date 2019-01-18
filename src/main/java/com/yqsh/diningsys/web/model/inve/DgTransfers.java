package com.yqsh.diningsys.web.model.inve;
/**
 * 调拨实体
 * @author jianglei
 * 日期 2016年10月25日 下午2:02:25
 *
 */
@SuppressWarnings("serial")
public class DgTransfers extends DgBaseInventory{
	private String id;          //编号
	private String outWareId;   //调出仓库编号
	private String inWareId;    //调入仓库编号
	private String inveId;      //库存编号
	private String remark;      //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOutWareId() {
		return outWareId;
	}
	public void setOutWareId(String outWareId) {
		this.outWareId = outWareId;
	}
	public String getInWareId() {
		return inWareId;
	}
	public void setInWareId(String inWareId) {
		this.inWareId = inWareId;
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
	public DgTransfers() {
		super();
	}
}
