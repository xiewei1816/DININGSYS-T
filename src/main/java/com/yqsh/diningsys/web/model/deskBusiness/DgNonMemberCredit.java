package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * 非会员挂账信息
 * @author ls001
 *table 'dg_non_member_credit'
 */
public class DgNonMemberCredit extends BasePojo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4314244699930505420L;
	private Integer id;
	private Integer memberId;			//非会员
	private DgNonMember nonMember;		//非会员实体
	private String documentNo; 			//单据号
	private Integer documentType;		//单据类型
	private Double aymentAmount;		//付款金额
	private Double creditAmount;		//挂账金额
	private Double paidAmount;			//实收金额
	private Double  discountAmount;		//优惠金额
	private String paymentTime;			//付款时间
	private String payStartTime;		//付款开始时间
	private String payEndTime;			//付款开始时间
	private Integer operatorId;			//操作员
	private SysUser operator;			//操作员实体
	private String settlementFlowNum;	//结算流水编号
	private String explains;			//说明
	private String createTime;			//创建时间
	private String createUserid;		//创建数据用户
	private SysUser createUser;			//创建用户对象
	private Integer delFlag;			//删除标记（0未删除 1已删除）
	private String conditions; 			//多查询条件
	private String crStartTime;			//创建开始时间（查询条件）
	private String crEndTime;			//创建结束时间（查询条件）
	private String checkName;
	
	public SysUser getOperator() {
		return operator;
	}
	public void setOperator(SysUser operator) {
		this.operator = operator;
	}
	public String getPayStartTime() {
		return payStartTime;
	}
	public String getPayEndTime() {
		return payEndTime;
	}
	public void setPayStartTime(String payStartTime) {
		this.payStartTime = payStartTime;
	}
	public void setPayEndTime(String payEndTime) {
		this.payEndTime = payEndTime;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getId() {
		return id;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public DgNonMember getNonMember() {
		return nonMember;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public Integer getDocumentType() {
		return documentType;
	}
	public Double getAymentAmount() {
		return aymentAmount;
	}
	public Double getCreditAmount() {
		return creditAmount;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public String getSettlementFlowNum() {
		return settlementFlowNum;
	}
	public String getExplains() {
		return explains;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public SysUser getCreateUser() {
		return createUser;
	}
	public Integer getDelFlag() {
		return delFlag;
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
	public String getCheckName() {
		return checkName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public void setNonMember(DgNonMember nonMember) {
		this.nonMember = nonMember;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}
	public void setAymentAmount(Double aymentAmount) {
		this.aymentAmount = aymentAmount;
	}
	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public void setSettlementFlowNum(String settlementFlowNum) {
		this.settlementFlowNum = settlementFlowNum;
	}
	public void setExplains(String explains) {
		this.explains = explains;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public void setCreateUser(SysUser createUser) {
		this.createUser = createUser;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
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
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
}
