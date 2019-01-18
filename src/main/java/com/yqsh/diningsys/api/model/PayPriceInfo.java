package com.yqsh.diningsys.api.model;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2017-01-05 17:22
 */
public class PayPriceInfo {

    private Double pxdzItemFilePrice;

    private Double pxdzItemCostsSum;

    private Double zyhdItemFilePrice;

    private Double zyhdItemCostsSum;

    private Double hydzItemFilePrice;

    private Double hydzItemCostsSum;

    public Double getPxdzItemFilePrice() {
        return pxdzItemFilePrice;
    }

    public void setPxdzItemFilePrice(Double pxdzItemFilePrice) {
        this.pxdzItemFilePrice = pxdzItemFilePrice;
    }

    public Double getPxdzItemCostsSum() {
        return pxdzItemCostsSum;
    }

    public void setPxdzItemCostsSum(Double pxdzItemCostsSum) {
        this.pxdzItemCostsSum = pxdzItemCostsSum;
    }

    public Double getZyhdItemFilePrice() {
        return zyhdItemFilePrice;
    }

    public void setZyhdItemFilePrice(Double zyhdItemFilePrice) {
        this.zyhdItemFilePrice = zyhdItemFilePrice;
    }

    public Double getZyhdItemCostsSum() {
        return zyhdItemCostsSum;
    }

    public void setZyhdItemCostsSum(Double zyhdItemCostsSum) {
        this.zyhdItemCostsSum = zyhdItemCostsSum;
    }

    public Double getHydzItemFilePrice() {
        return hydzItemFilePrice;
    }

    public void setHydzItemFilePrice(Double hydzItemFilePrice) {
        this.hydzItemFilePrice = hydzItemFilePrice;
    }

    public Double getHydzItemCostsSum() {
        return hydzItemCostsSum;
    }

    public void setHydzItemCostsSum(Double hydzItemCostsSum) {
        this.hydzItemCostsSum = hydzItemCostsSum;
    }
}
