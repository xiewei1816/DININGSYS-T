package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import java.math.BigDecimal;
import java.util.Date;


public class DgWaterCoupon {
    private Integer id;

    private Integer owid; //营业流水id

    private String couponval; //券面值

    private BigDecimal yhmoney; //优惠金额

    private Integer cwid; //结算id

    private Date createtime; //创建时间

    private Date usetime; //使用时间

    private Integer state; //状态

    private Integer coupontype; // 券类型 0 现金券  1折扣券  2菜品券

    private BigDecimal pxdzYhSutotal; //品项打折优惠合计

    private BigDecimal zyhdYhSutotal; //重要活动优惠合计

    private BigDecimal hyYhSutotal; //会员优惠合计

    private String couponInfo;//券信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwid() {
        return owid;
    }

    public void setOwid(Integer owid) {
        this.owid = owid;
    }

    public String getCouponval() {
        return couponval;
    }

    public void setCouponval(String couponval) {
        this.couponval = couponval == null ? null : couponval.trim();
    }

    public BigDecimal getYhmoney() {
        return yhmoney;
    }

    public void setYhmoney(BigDecimal yhmoney) {
        this.yhmoney = yhmoney;
    }

    public Integer getCwid() {
        return cwid;
    }

    public void setCwid(Integer cwid) {
        this.cwid = cwid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUsetime() {
        return usetime;
    }

    public void setUsetime(Date usetime) {
        this.usetime = usetime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(Integer coupontype) {
        this.coupontype = coupontype;
    }

    public BigDecimal getPxdzYhSutotal() {
        return pxdzYhSutotal;
    }

    public void setPxdzYhSutotal(BigDecimal pxdzYhSutotal) {
        this.pxdzYhSutotal = pxdzYhSutotal;
    }

    public BigDecimal getZyhdYhSutotal() {
        return zyhdYhSutotal;
    }

    public void setZyhdYhSutotal(BigDecimal zyhdYhSutotal) {
        this.zyhdYhSutotal = zyhdYhSutotal;
    }

    public BigDecimal getHyYhSutotal() {
        return hyYhSutotal;
    }

    public void setHyYhSutotal(BigDecimal hyYhSutotal) {
        this.hyYhSutotal = hyYhSutotal;
    }

    public String getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        this.couponInfo = couponInfo;
    }
}