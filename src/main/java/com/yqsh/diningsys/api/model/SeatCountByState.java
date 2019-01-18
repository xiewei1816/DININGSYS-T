package com.yqsh.diningsys.api.model;

/**
 * 客位状态统计信息
 */
public class SeatCountByState {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态名称
     */
    private String name;

    /**
     * 具体值
     */
    private String value;

    public SeatCountByState(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        switch (code){
            case 1:
                return "空闲";
            case 2:
                return "占用";
            case 3:
                return "清扫";
            case 4:
                return "预定";
            case 5:
                return "埋单";
            default:
                return null;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
            this.value = value;
        }

}
