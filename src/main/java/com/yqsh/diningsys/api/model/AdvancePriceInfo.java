package com.yqsh.diningsys.api.model;

/**
 * 埋单有优惠的时候，存入的两种价格
 *
 * @author zhshuo create on 2017-01-06 10:27
 */
public class AdvancePriceInfo {

    private Double openWaterZyhdSubtotal;

    private Double zyhdZeroMonry;

    private Double openWaterZyhdYs;

    private Double openWaterPxdzSubtotal;

    private Double pxdzZeroMonry;

    private Double openWaterPxdzYs;

    public Double getOpenWaterZyhdSubtotal() {
        return openWaterZyhdSubtotal;
    }

    public void setOpenWaterZyhdSubtotal(Double openWaterZyhdSubtotal) {
        this.openWaterZyhdSubtotal = openWaterZyhdSubtotal;
    }

    public Double getZyhdZeroMonry() {
        return zyhdZeroMonry;
    }

    public void setZyhdZeroMonry(Double zyhdZeroMonry) {
        this.zyhdZeroMonry = zyhdZeroMonry;
    }

    public Double getOpenWaterZyhdYs() {
        return openWaterZyhdYs;
    }

    public void setOpenWaterZyhdYs(Double openWaterZyhdYs) {
        this.openWaterZyhdYs = openWaterZyhdYs;
    }

    public Double getOpenWaterPxdzSubtotal() {
        return openWaterPxdzSubtotal;
    }

    public void setOpenWaterPxdzSubtotal(Double openWaterPxdzSubtotal) {
        this.openWaterPxdzSubtotal = openWaterPxdzSubtotal;
    }

    public Double getPxdzZeroMonry() {
        return pxdzZeroMonry;
    }

    public void setPxdzZeroMonry(Double pxdzZeroMonry) {
        this.pxdzZeroMonry = pxdzZeroMonry;
    }

    public Double getOpenWaterPxdzYs() {
        return openWaterPxdzYs;
    }

    public void setOpenWaterPxdzYs(Double openWaterPxdzYs) {
        this.openWaterPxdzYs = openWaterPxdzYs;
    }
}
