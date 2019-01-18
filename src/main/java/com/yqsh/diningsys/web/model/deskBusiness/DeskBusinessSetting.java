package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;

import java.io.Serializable;

/**
 * 前台营业设置
 * Created by mrren on 2016/11/11.
 */
public class DeskBusinessSetting extends BasePojo implements Serializable {

    private Integer id;

    //客座设置
    private String seatServ;

    //账单设置
    private String billServ;

    //打印设置
    private String printerSetting;

    //单据生成规则
    private String serialRul;

    //雅座设置
    private String loungeSetting;

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

    public String getSeatServ() {
        return seatServ;
    }

    public void setSeatServ(String seatServ) {
        this.seatServ = seatServ;
    }

    public String getBillServ() {
        return billServ;
    }

    public void setBillServ(String billServ) {
        this.billServ = billServ;
    }

    public String getSerialRul() {
        return serialRul;
    }

    public void setSerialRul(String serialRul) {
        this.serialRul = serialRul;
    }

    public String getLoungeSetting() {
        return loungeSetting;
    }

    public void setLoungeSetting(String loungeSetting) {
        this.loungeSetting = loungeSetting;
    }

    public String getPrinterSetting() {
        return printerSetting;
    }

    public void setPrinterSetting(String printerSetting) {
        this.printerSetting = printerSetting;
    }

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
    
    
}
