package com.yqsh.diningsys.web.service.api.impl;

import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgWaterCouponMapper;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgWaterCoupon;
import com.yqsh.diningsys.web.service.api.DgWaterCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DgWaterCouponServiceImpl implements DgWaterCouponService {

    @Autowired
    private DgWaterCouponMapper dgWaterCouponMapper;

    @Override
    public int getCouponCountByWatersAndCouponType(List<DgOpenWater> dgOpenWaters, Integer couponType) {
        return dgWaterCouponMapper.getCouponCountByWatersAndCouponType(dgOpenWaters,couponType);
    }

    @Override
    public List<DgWaterCoupon> getCouponCountByWaters(List<DgOpenWater> dgOpenWaters) {
        return dgWaterCouponMapper.getCouponCountByWaters(dgOpenWaters);
    }

    @Override
    public DgWaterCoupon selectByCouponVal(String couponVal) {
        return dgWaterCouponMapper.selectByCouponVal(couponVal);
    }

    @Override
    public void insertCoupon(Integer owid, String couponval,String couponInfo,BigDecimal yhmoney, Date createtime, Integer coupontype,BigDecimal pxdzYhSutotal,BigDecimal zyhdYhSutotal,BigDecimal hyYhSutotal) {
        DgWaterCoupon dgWaterCoupon = new DgWaterCoupon();
        dgWaterCoupon.setOwid(owid);
        dgWaterCoupon.setCoupontype(coupontype);
        dgWaterCoupon.setCouponval(couponval);
        dgWaterCoupon.setYhmoney(yhmoney);
        dgWaterCoupon.setCreatetime(createtime);
        dgWaterCoupon.setPxdzYhSutotal(pxdzYhSutotal);
        dgWaterCoupon.setZyhdYhSutotal(zyhdYhSutotal);
        dgWaterCoupon.setHyYhSutotal(hyYhSutotal);
        dgWaterCoupon.setCouponInfo(couponInfo);
        dgWaterCouponMapper.insert(dgWaterCoupon);
    }

    @Override
    public void updateCouponService(DgWaterCoupon dgWaterCoupon) {
        dgWaterCouponMapper.updateByPrimaryKey(dgWaterCoupon);
    }
}
