package com.yqsh.diningsys.web.model.deskBusiness;

/**
 * 雅座设置dto
 * Created by mrren on 2016/11/15.
 */
public class DBSLoungeSettingDTO {
    private String isEnableLoungeInterface;                            //是否启用雅座接口
    private String operator;                                           //操作员
    private String operatorPassword;                                   //操作员密码
    private String loungeDirectorPassword;                              //雅座主管密码

    public String getIsEnableLoungeInterface() {
        return isEnableLoungeInterface;
    }

    public void setIsEnableLoungeInterface(String isEnableLoungeInterface) {
        this.isEnableLoungeInterface = isEnableLoungeInterface;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorPassword() {
        return operatorPassword;
    }

    public void setOperatorPassword(String operatorPassword) {
        this.operatorPassword = operatorPassword;
    }

    public String getLoungeDirectorPassword() {
        return loungeDirectorPassword;
    }

    public void setLoungeDirectorPassword(String loungeDirectorPassword) {
        this.loungeDirectorPassword = loungeDirectorPassword;
    }
}
