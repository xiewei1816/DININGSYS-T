package com.yqsh.diningsys.web.dao.deskBusiness;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitP;
@Repository
public interface DgItemTimeLimitPMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemTimeLimitP record);

    int insertSelective(DgItemTimeLimitP record);

    DgItemTimeLimitP selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemTimeLimitP record);

    int updateByPrimaryKey(DgItemTimeLimitP record);
    
    int deleteAll();
    
    int insertBackId(DgItemTimeLimitP record);
}