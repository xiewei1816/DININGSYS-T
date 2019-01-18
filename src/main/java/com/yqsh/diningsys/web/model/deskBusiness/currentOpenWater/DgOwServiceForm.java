package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.DateUtil;
import org.apache.http.client.utils.DateUtils;

import java.util.Date;

/**
 * Created on 2017-04-26 13:49
 *
 * @author zhshuo
 */
public class DgOwServiceForm {

    private Integer id;

    private String serviceNum;

    private Integer owId;

    private Integer waiterId;

    private String servieTime;

    private String serviceRoundtrip;

    private Integer serviceType;

    private String zdbz;

    private String uuid;

    private String shopKey;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum;
    }

    public Integer getOwId() {
        return owId;
    }

    public void setOwId(Integer owId) {
        this.owId = owId;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public String getServieTime() {
        return servieTime;
    }

    public void setServieTime(String servieTime) {
        this.servieTime = servieTime;
    }

    public String getServiceRoundtrip() {
        return serviceRoundtrip;
    }

    public void setServiceRoundtrip(String serviceRoundtrip) {
        this.serviceRoundtrip = serviceRoundtrip;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getZdbz() {
        return zdbz;
    }

    public void setZdbz(String zdbz) {
        this.zdbz = zdbz;
    }

    @Override
    public String toString() {
        return "DgOwServiceForm{" +
                "id=" + id +
                ", serviceNum='" + serviceNum + '\'' +
                ", owId=" + owId +
                ", waiterId=" + waiterId +
                ", servieTime=" + servieTime +
                ", serviceRoundtrip='" + serviceRoundtrip + '\'' +
                ", serviceType=" + serviceType +
                ", zdbz='" + zdbz + '\'' +
                '}';
    }
}
