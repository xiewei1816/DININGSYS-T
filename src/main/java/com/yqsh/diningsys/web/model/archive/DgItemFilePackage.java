package com.yqsh.diningsys.web.model.archive;

import java.util.List;
import java.util.Map;

public class DgItemFilePackage {
    private Integer id;

    private Integer itemFileId;

    private Integer packageType;

    private Integer packageBanquet;

    private Integer packageSum;

    private Integer packageAmountSum;

    private Double packageStandardpriceSum;

    private Double packageStandardpriceSumNum;

    private Double itemFileAddprice;

    private Integer canupdateItemNum;

    private Integer minNum;

    private Integer maxNum;


    private List<Map> dgItemFilePackageBx;
    private List<Map> dgItemFilePackageKx;
    private List<Map> dgItemFilePackageSlxd;
    private List<Map> dgItemFilePackageTh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }

    public Integer getPackageBanquet() {
        return packageBanquet;
    }

    public void setPackageBanquet(Integer packageBanquet) {
        this.packageBanquet = packageBanquet;
    }

    public Integer getPackageSum() {
        return packageSum;
    }

    public void setPackageSum(Integer packageSum) {
        this.packageSum = packageSum;
    }

    public Integer getPackageAmountSum() {
        return packageAmountSum;
    }

    public void setPackageAmountSum(Integer packageAmountSum) {
        this.packageAmountSum = packageAmountSum;
    }

    public Double getPackageStandardpriceSum() {
        return packageStandardpriceSum;
    }

    public void setPackageStandardpriceSum(Double packageStandardpriceSum) {
        this.packageStandardpriceSum = packageStandardpriceSum;
    }

    public Double getPackageStandardpriceSumNum() {
        return packageStandardpriceSumNum;
    }

    public void setPackageStandardpriceSumNum(Double packageStandardpriceSumNum) {
        this.packageStandardpriceSumNum = packageStandardpriceSumNum;
    }

    public Double getItemFileAddprice() {
        return itemFileAddprice;
    }

    public void setItemFileAddprice(Double itemFileAddprice) {
        this.itemFileAddprice = itemFileAddprice;
    }

    public Integer getCanupdateItemNum() {
        return canupdateItemNum;
    }

    public void setCanupdateItemNum(Integer canupdateItemNum) {
        this.canupdateItemNum = canupdateItemNum;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

	public List<Map> getDgItemFilePackageBx() {
		return dgItemFilePackageBx;
	}

	public void setDgItemFilePackageBx(List<Map> dgItemFilePackageBx) {
		this.dgItemFilePackageBx = dgItemFilePackageBx;
	}

	public List<Map> getDgItemFilePackageKx() {
		return dgItemFilePackageKx;
	}

	public void setDgItemFilePackageKx(List<Map> dgItemFilePackageKx) {
		this.dgItemFilePackageKx = dgItemFilePackageKx;
	}

	public List<Map> getDgItemFilePackageSlxd() {
		return dgItemFilePackageSlxd;
	}

	public void setDgItemFilePackageSlxd(List<Map> dgItemFilePackageSlxd) {
		this.dgItemFilePackageSlxd = dgItemFilePackageSlxd;
	}

	public List<Map> getDgItemFilePackageTh() {
		return dgItemFilePackageTh;
	}

	public void setDgItemFilePackageTh(List<Map> dgItemFilePackageTh) {
		this.dgItemFilePackageTh = dgItemFilePackageTh;
	}


    
    
}