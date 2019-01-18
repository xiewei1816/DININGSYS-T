package com.yqsh.diningsys.web.model.businessMan;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 消费品项显示设置
 * 此表记录品项是否显示、是否排行
 * @author xiewei
 *
 */
public class DgItemShowRank extends BasePojo{

	private Integer id;

    private Integer pxId;    //品项基础表ID
    
    private String isShow;  //是否显示

    private String isRank;  //是否排行
    
    private String pxlx;    //品项类型 ：1= 品项小类、0=品项
    
    private Integer rank;   //之间戳

    private Date createTime;  //创建时间
    
    private Date updateTime;  //修改时间

	private String uuidKey;

	private String shopKey;
	public String getUuidKey() {
		return uuidKey;
	}

	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPxId() {
		return pxId;
	}

	public void setPxId(Integer pxId) {
		this.pxId = pxId;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsRank() {
		return isRank;
	}

	public void setIsRank(String isRank) {
		this.isRank = isRank;
	}

	public String getPxlx() {
		return pxlx;
	}

	public void setPxlx(String pxlx) {
		this.pxlx = pxlx;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getCreateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(createTime != null)
            return simpleDateFormat.format(createTime);
        else return null;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(updateTime != null)
            return simpleDateFormat.format(updateTime);
        else return null;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
	
	
}