package com.yqsh.diningsys.web.service.api;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgWaterCoupon;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface DgWaterCouponService {

    //获取打折券数量
    int getCouponCountByWatersAndCouponType(List<DgOpenWater> dgOpenWaters,Integer couponType);

    List<DgWaterCoupon> getCouponCountByWaters(List<DgOpenWater> dgOpenWaters);

    DgWaterCoupon selectByCouponVal(String couponVal);

    void insertCoupon(Integer owid, String couponval,String couponInfo, BigDecimal yhmoney, Date createtime, Integer coupontype,BigDecimal pxdzYhSutotal,BigDecimal zyhdYhSutotal,BigDecimal hyYhSutotal);

    void updateCouponService(DgWaterCoupon dgWaterCoupon);
}
