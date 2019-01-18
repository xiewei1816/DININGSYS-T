package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOffset;

public interface DgOffsetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgOffset record);

    int insertSelective(DgOffset record);

    DgOffset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgOffset record);

    int updateByPrimaryKeyWithBLOBs(DgOffset record);

    int updateByPrimaryKey(DgOffset record);

    Integer getCount();

    DgOffset getFirstItem();
}