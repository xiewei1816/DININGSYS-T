package com.yqsh.diningsys.web.model.inve;

import java.math.BigDecimal;

/**
 * 盘点实体
 * @author jianglei
 * 日期 2016年10月26日 上午11:28:57
 *
 */
@SuppressWarnings("serial")
public class DgDiscPoint extends DgBaseInventory{
	/**
	 * 流水位数:值是多少就表示多少位
	 */
	public static final int SERIA_DIGITS=4;
	private String id;            //编号
	private String inveId;        //库存编号
	private BigDecimal diffNum;   //差异数量
	private BigDecimal diffAmount;//差异金额
	private String remark;        //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInveId() {
		return inveId;
	}
	public void setInveId(String inveId) {
		this.inveId = inveId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getDiffNum() {
		return diffNum;
	}
	public void setDiffNum(BigDecimal diffNum) {
		this.diffNum = diffNum;
	}
	public BigDecimal getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}
	public DgDiscPoint() {
		super();
	}
}
