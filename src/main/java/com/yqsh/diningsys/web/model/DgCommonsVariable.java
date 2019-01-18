package com.yqsh.diningsys.web.model;

/**
 * 公共变量
 *
 * @author zhshuo create on 2016-11-16 上午9:39
 */
public class DgCommonsVariable {

    private String cvCode;

    private String cvValue;

    public DgCommonsVariable() {
    }

    public DgCommonsVariable(String cvCode, String cvValue) {
        this.cvCode = cvCode;
        this.cvValue = cvValue;
    }

    public String getCvCode() {
        return cvCode;
    }

    public void setCvCode(String cvCode) {
        this.cvCode = cvCode;
    }

    public String getCvValue() {
        return cvValue;
    }

    public void setCvValue(String cvValue) {
        this.cvValue = cvValue;
    }
}
