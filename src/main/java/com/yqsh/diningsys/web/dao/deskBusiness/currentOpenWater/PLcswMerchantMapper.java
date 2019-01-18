package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.PLcswMerchant;

public interface PLcswMerchantMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PLcswMerchant record);

    int insertSelective(PLcswMerchant record);

    PLcswMerchant selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PLcswMerchant record);

    int updateByPrimaryKey(PLcswMerchant record);

    //选择一个
    PLcswMerchant selectOne();
}