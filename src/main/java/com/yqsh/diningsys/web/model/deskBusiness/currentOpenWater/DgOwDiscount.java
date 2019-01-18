package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

public class DgOwDiscount{
    private String discountInfo;

    private String authorizedPerson;

    private String authorizedPersonName;

    private Integer owId;

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public String getAuthorizedPerson() {
        return authorizedPerson;
    }

    public void setAuthorizedPerson(String authorizedPerson) {
        this.authorizedPerson = authorizedPerson;
    }

    public String getAuthorizedPersonName() {
        return authorizedPersonName;
    }

    public void setAuthorizedPersonName(String authorizedPersonName) {
        this.authorizedPersonName = authorizedPersonName == null ? null : authorizedPersonName.trim();
    }

    public Integer getOwId() {
        return owId;
    }

    public void setOwId(Integer owId) {
        this.owId = owId;
    }
}