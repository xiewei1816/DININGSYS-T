package com.yqsh.diningsys.web.model.deskBusiness;

/**
 * 营业流水
 * Created by mrren on 2016/11/25.
 */
public class DBSJournalBusiness {

    private String isNeedPrefix;               //营业流水号前缀

    private String prefix;              //营业流水号前缀内容

    private String isNeedOrganCode;                 //是否需要组织机构编码

    private String isNeedDateString;                //是否需要日期

    private String dateFormat;                       //日期格式

    private String isOrderBeginWithOne;                   //是否每日从1开始

    public String getIsNeedPrefix() {
        return isNeedPrefix;
    }

    public void setIsNeedPrefix(String isNeedPrefix) {
        this.isNeedPrefix = isNeedPrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getIsNeedOrganCode() {
        return isNeedOrganCode;
    }

    public void setIsNeedOrganCode(String isNeedOrganCode) {
        this.isNeedOrganCode = isNeedOrganCode;
    }

    public String getIsNeedDateString() {
        return isNeedDateString;
    }

    public void setIsNeedDateString(String isNeedDateString) {
        this.isNeedDateString = isNeedDateString;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getIsOrderBeginWithOne() {
        return isOrderBeginWithOne;
    }

    public void setIsOrderBeginWithOne(String isOrderBeginWithOne) {
        this.isOrderBeginWithOne = isOrderBeginWithOne;
    }
}
