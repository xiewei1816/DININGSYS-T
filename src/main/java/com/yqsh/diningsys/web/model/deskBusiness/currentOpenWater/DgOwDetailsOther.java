package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

public class DgOwDetailsOther {
    private Integer sfId;

    private Integer itemId;

    private Integer otype;

    private String ocode;

    private String oname;

    private Double ocosts;

    private Integer zzffSf;

    private Integer zzffSfType;

    public Integer getSfId() {
        return sfId;
    }

    public void setSfId(Integer sfId) {
        this.sfId = sfId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getOtype() {
        return otype;
    }

    public void setOtype(Integer otype) {
        this.otype = otype;
    }

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode == null ? null : ocode.trim();
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname == null ? null : oname.trim();
    }

    public Double getOcosts() {
        return ocosts;
    }

    public void setOcosts(Double ocosts) {
        this.ocosts = ocosts;
    }

    public Integer getZzffSf() {
        return zzffSf;
    }

    public void setZzffSf(Integer zzffSf) {
        this.zzffSf = zzffSf;
    }

    public Integer getZzffSfType() {
        return zzffSfType;
    }

    public void setZzffSfType(Integer zzffSfType) {
        this.zzffSfType = zzffSfType;
    }
}