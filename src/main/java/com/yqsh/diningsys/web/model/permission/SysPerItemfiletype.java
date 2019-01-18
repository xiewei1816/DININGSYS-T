package com.yqsh.diningsys.web.model.permission;

public class SysPerItemfiletype {
    private Integer pxxlId;

    private String zwCode;

    private Integer type;

    private String uuidKey;

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public Integer getPxxlId() {
        return pxxlId;
    }

    public void setPxxlId(Integer pxxlId) {
        this.pxxlId = pxxlId;
    }

    public String getZwCode() {
        return zwCode;
    }

    public void setZwCode(String zwCode) {
        this.zwCode = zwCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}