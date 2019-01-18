package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwModifySeat;
import org.springframework.stereotype.Repository;

@Repository
public interface DgOwModifySeatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgOwModifySeat record);

    int insertSelective(DgOwModifySeat record);

    DgOwModifySeat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgOwModifySeat record);

    int updateByPrimaryKey(DgOwModifySeat record);
    
    DgOwModifySeat selectByOwNum(String owNum);
}