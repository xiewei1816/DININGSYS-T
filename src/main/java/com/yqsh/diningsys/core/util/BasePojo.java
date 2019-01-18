package com.yqsh.diningsys.core.util;

import java.io.Serializable;
import java.util.List;

public class BasePojo implements Serializable {
	/**
	 * 状态,state:0表示正常
	 */
	public static final String STATE_NORMAL="0";
	/**
	 * 状态，state:1表示已删除
	 */
	public static final String STATE_DEL="1";
	private static final long serialVersionUID = 64006569908099183L;

	/** 当前�? */
	private Integer page =1;
	
	private List<String> ids;
	
	private Long totalPage;
	
	/** 页面容量. */
	private Integer rows = 15;
	
	/** 是否�?��分页. */
	private boolean showByPage = true;
	
	/** 起始行数. */
	private Integer startRow = 0;
	
	/** 结束行数. */
	private Integer endRow = 0;

	/** 总行�? */
	private Long totalRow = 0L;
	
	private String pageHtml = "";
	
	private Object srows;
	
	private String columnValues;
	
	private String condition; //autoCom查询插件的条件
	
	private String fixedCon; //autoCom查询插件固定的查询条件
	
	private String controlId; //autoCom查询插件返回的id值
	
	private String controlName; //autoCom查询插件返回的name值
	
	private String editIds; //删除数据时的ids集合值
	
	private String crStartTime; //日期查询条件
	private String crEndTime; //日期查询条件
	private String operUser;  //操作人
	private String createDate;//创建时间
	
	/** 返回参数 */
	protected int retcode;
	private String sidx; //排序字段
	private String sord; //排序方式
	
	public String getSidx() {
		return sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getEditIds() {
		return editIds;
	}

	public void setEditIds(String editIds) {
		this.editIds = editIds;
	}

	public String getFixedCon() {
		return fixedCon;
	}

	public void setFixedCon(String fixedCon) {
		this.fixedCon = fixedCon;
	}

	public String getControlId() {
		return controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getColumnValues() {
		return columnValues;
	}

	public void setColumnValues(String columnValues) {
		this.columnValues = columnValues;
	}

	public Object getSrows() {
		return srows;
	}

	public void setSrows(Object srows) {
		this.srows = srows;
	}

	public Integer getPage() {
		return page;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getRows() {
		return rows;
	}

	public Integer getStartRow() {
		return  (page-1)*rows;
	}

	public Long getTotalRow() {
		return totalRow;
	}

	public boolean isShowByPage() {
		return showByPage;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public void setShowByPage(boolean showByPage) {
		this.showByPage = showByPage;
	}

	public void setStartRow() {
		this.startRow = startRow;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setTotalRow(Long totalRow) {
		this.totalRow = totalRow;
		if( this.totalRow % rows == 0 ){
			this.totalPage = this.totalRow / rows;
		}else{
			this.totalPage = this.totalRow / rows + 1;
		}
	}

	public Integer getEndRow() {
		return page*rows;
	}

	public void setEndRow() {
		this.endRow = endRow;
	}

	public String getPageHtml() {
		return pageHtml;
	}

	public void setPageHtml() {
		if(totalRow <= rows) {
			pageHtml = "";
		} else {
			StringBuffer sb = new StringBuffer();
			if(page > 1) {
				sb.append("<a href=\"javascript:toPage('"+(page-1)+"');\"><</a>");
			}
			for(int i = 0; i < totalPage; i++) {
				if(page == (i+1)) {
					sb.append("<a href=\"javascript:toPage('"+(i+1)+"');\" class='now'>" + (i+1) + "</a>");
				} else {
					sb.append("<a href=\"javascript:toPage('"+(i+1)+"');\">" + (i+1) + "</a>");
				}
			}
			if(page < totalPage) {
				sb.append("<a href=\"javascript:toPage('"+(page+1)+"');\">></a>");
			}
			pageHtml = sb.toString();
		}
	}
	
	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getRetcode() {
		return retcode;
	}

	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public String getCrStartTime() {
		return crStartTime;
	}

	public void setCrStartTime(String crStartTime) {
		this.crStartTime = crStartTime;
	}

	public String getCrEndTime() {
		return crEndTime;
	}

	public void setCrEndTime(String crEndTime) {
		this.crEndTime = crEndTime;
	}
}
