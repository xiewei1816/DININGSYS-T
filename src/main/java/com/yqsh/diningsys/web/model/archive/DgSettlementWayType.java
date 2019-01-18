package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * 结算方式类型
 * @author ls001
 *
 */
public class DgSettlementWayType extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -418987668886825326L;
	private Integer id;
	private String name;
	private Integer hasRollFace;						//是否含有‘卷面值’（1是，0否）
	private Integer hasActualIncomeRatio;				//是否含有‘计入实际收入比例’（1是，0否）
	private Integer hasNotActualIncomeRatio;			//是否含有‘不计入实际收入比例’（1是，0否）
	private Integer hasCurrency;						//是否含有‘本位币’（1是，0否）
	private Integer hasAllowChange;						//是否含有‘允许找零’（1是，0否）
	private String explains;							//说明
	private Integer delFlag;							//删除标记（0未删除 1已删除）
	private String createUserid;						//创建数据用户
	private SysUser createUser;							//创建用户对象
	private String createTime;							//创建时间
	private String conditions; 							//多查询条件
	private String crStartTime;							//创建开始时间（查询条件）
	private String crEndTime;							//创建结束时间（查询条件）
	private String checkName;							//检查名称时使用
	
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getConditions() {
		return conditions;
	}
	public String getCrStartTime() {
		return crStartTime;
	}
	public String getCrEndTime() {
		return crEndTime;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public void setCrStartTime(String crStartTime) {
		this.crStartTime = crStartTime;
	}
	public void setCrEndTime(String crEndTime) {
		this.crEndTime = crEndTime;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getHasRollFace() {
		return hasRollFace;
	}
	public Integer getHasActualIncomeRatio() {
		return hasActualIncomeRatio;
	}
	public Integer getHasNotActualIncomeRatio() {
		return hasNotActualIncomeRatio;
	}
	public Integer getHasCurrency() {
		return hasCurrency;
	}
	public Integer getHasAllowChange() {
		return hasAllowChange;
	}
	public String getExplains() {
		return explains;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public SysUser getCreateUser() {
		return createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setHasRollFace(Integer hasRollFace) {
		this.hasRollFace = hasRollFace;
	}
	public void setHasActualIncomeRatio(Integer hasActualIncomeRatio) {
		this.hasActualIncomeRatio = hasActualIncomeRatio;
	}
	public void setHasNotActualIncomeRatio(Integer hasNotActualIncomeRatio) {
		this.hasNotActualIncomeRatio = hasNotActualIncomeRatio;
	}
	public void setHasCurrency(Integer hasCurrency) {
		this.hasCurrency = hasCurrency;
	}
	public void setHasAllowChange(Integer hasAllowChange) {
		this.hasAllowChange = hasAllowChange;
	}
	public void setExplains(String explains) {
		this.explains = explains;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public void setCreateUser(SysUser createUser) {
		this.createUser = createUser;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
