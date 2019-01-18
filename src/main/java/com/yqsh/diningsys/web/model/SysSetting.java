package com.yqsh.diningsys.web.model;

public class SysSetting {
    private String settingCode;

    private String settingValue;

    public String getSettingCode() {
        return settingCode;
    }

    public void setSettingCode(String settingCode) {
        this.settingCode = settingCode == null ? null : settingCode.trim();
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue == null ? null : settingValue.trim();
    }
}