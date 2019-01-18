package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;

public interface DgReceptionClearingWaterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgReceptionClearingWater record);

    int insertSelective(DgReceptionClearingWater record);

    DgReceptionClearingWater selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgReceptionClearingWater record);

    int updateByPrimaryKey(DgReceptionClearingWater record);
}