package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 公共代码实体
 * @author xiewei
 *
 */
public class DgPublicCode0 extends BasePojo{
	/**
	 * 公共代码中，数据同步code:048
	 */
	public static final String SYNDATA_CCODE="048";
	
    private Integer id;  //ID-自增
    
    private String cCode;  //代码

    private String cName;  //名称
    
    private String cKeyWD;  //速记码
    
    private String cParent;  //上级代码
    
    private Integer cOrder;  //排序值

    private String iSystem;  //是否系统公共代码 ： “Y”-是，”N“或”null“不是

    private String iDelFlg;  //是否删除 默认“0”-未删除 “1”-已删除

    private String cParentName;//上级代码名称


	private Integer perBusId;

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

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcKeyWD() {
		return cKeyWD;
	}

	public void setcKeyWD(String cKeyWD) {
		this.cKeyWD = cKeyWD;
	}

	public Integer getcOrder() {
		return cOrder;
	}

	public void setcOrder(Integer cOrder) {
		this.cOrder = cOrder;
	}

	public String getcParent() {
		return cParent;
	}

	public void setcParent(String cParent) {
		this.cParent = cParent;
	}

	public String getiSystem() {
		return iSystem;
	}

	public void setiSystem(String iSystem) {
		this.iSystem = iSystem;
	}

	public String getiDelFlg() {
		return iDelFlg;
	}

	public void setiDelFlg(String iDelFlg) {
		this.iDelFlg = iDelFlg;
	}

    public void getcKeyWD(String pyIndexStr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public String getcParentName() {
		return cParentName;
	}

	public void setcParentName(String cParentName) {
		this.cParentName = cParentName;
	}



	public Integer getPerBusId() {
		return perBusId;
	}

	public void setPerBusId(Integer perBusId) {
		this.perBusId = perBusId;
	}
}