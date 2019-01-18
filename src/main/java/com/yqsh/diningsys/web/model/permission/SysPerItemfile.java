package com.yqsh.diningsys.web.model.permission;

public class SysPerItemfile {
    private Integer itemFileId;

    private String zwCode;

    private Integer type;

    private String uuidKey;

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
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