package com.yqsh.diningsys.web.model.inve;
/**
 * 部门用料管理实体
 * @author jianglei
 * 日期 2016年10月25日 上午9:55:59
 *
 */
@SuppressWarnings("serial")
public class DgDepaMaterial extends DgBaseInventory{
	/**
	 * 流水位数:值是多少就表示多少位
	 */
	public static final int SERIA_DIGITS=4;
	/**
	 * 类型:0表示部门领料
	 */
	public static final String MATETYPE_LED="0";
	/**
	 * 类型:1表示部门退料
	 */
	public static final String MATETYPE_REFUND="1";
	private String id;        //编号
	private String mateType;  //部门操作类型
	private String depaId;    //部门id
	private String inveId;    //库存编号
	private String remark;    //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMateType() {
		return mateType;
	}
	public void setMateType(String mateType) {
		this.mateType = mateType;
	}
	public String getInveId() {
		return inveId;
	}
	public void setInveId(String inveId) {
		this.inveId = inveId;
	}
	
	public String getDepaId() {
		return depaId;
	}
	public void setDepaId(String depaId) {
		this.depaId = depaId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public DgDepaMaterial() {
		super();
	}
}
