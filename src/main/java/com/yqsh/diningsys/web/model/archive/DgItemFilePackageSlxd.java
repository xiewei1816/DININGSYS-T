package com.yqsh.diningsys.web.model.archive;

/**
 * 品项档案套餐大类数量限定
 */
public class DgItemFilePackageSlxd {
    private Integer id;

    private Integer packageId;

    private Integer itemFileTypeId;

    private Integer maxNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getItemFileTypeId() {
        return itemFileTypeId;
    }

    public void setItemFileTypeId(Integer itemFileTypeId) {
        this.itemFileTypeId = itemFileTypeId;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }
}