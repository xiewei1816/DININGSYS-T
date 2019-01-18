package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgWaterCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DgWaterCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgWaterCoupon record);

    int insertSelective(DgWaterCoupon record);

    DgWaterCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgWaterCoupon record);

    int updateByPrimaryKey(DgWaterCoupon record);

    int getCouponCountByWatersAndCouponType(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters, @Param("couponType")Integer couponType);

    List<DgWaterCoupon> getCouponCountByWaters(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters);

    DgWaterCoupon selectByCouponVal(@Param("couponVal")String couponVal);
}