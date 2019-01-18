package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;


/**
 * 退菜原因实体
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:28:03
 */
public class TbRfc extends BasePojo{

	private Integer id;

    private String rfcType;    //退菜原因类型

    private String rfcCode;    //编号

    private String rfcName;  //名称
 
    private String rfcZjf;    //助记符
     
    private String rfcExplain;  //说明
    
    private String isMaterialLoss;  //原料损失

    private String isTdsgqpx;  //退单时沽清品项
    
    private String isDel;  //是否删除 0-未删除 1-已删除
    
    private Date createTime; //创建时间

	private String shopKey;

	private String uuidKey;

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

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

	public String getRfcType() {
		return rfcType;
	}

	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}

	public String getRfcCode() {
		return rfcCode;
	}

	public void setRfcCode(String rfcCode) {
		this.rfcCode = rfcCode;
	}

	public String getRfcName() {
		return rfcName;
	}

	public void setRfcName(String rfcName) {
		this.rfcName = rfcName;
	}

	public String getRfcZjf() {
		return rfcZjf;
	}

	public void setRfcZjf(String rfcZjf) {
		this.rfcZjf = rfcZjf;
	}

	public String getRfcExplain() {
		return rfcExplain;
	}

	public void setRfcExplain(String rfcExplain) {
		this.rfcExplain = rfcExplain;
	}

	public String getIsMaterialLoss() {
		return isMaterialLoss;
	}

	public void setIsMaterialLoss(String isMaterialLoss) {
		this.isMaterialLoss = isMaterialLoss;
	}

	public String getIsTdsgqpx() {
		return isTdsgqpx;
	}

	public void setIsTdsgqpx(String isTdsgqpx) {
		this.isTdsgqpx = isTdsgqpx;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
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
    
}