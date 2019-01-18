package com.yqsh.diningsys.web.model;

/**
 * controller 返回的json
 *
 * @author zhshuo create on 2016-07-21 13:45
 */
public class ResultInfo {

    private boolean success = true;

    private String successMsg;

    private String errorMsg;

    public ResultInfo() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.setSuccess(false);
        this.errorMsg = errorMsg;
    }
}
