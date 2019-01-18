package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
@Repository
public interface DgItemProDepMapper extends GenericDao<DgItemProDep,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemProDep record);

    int insertSelective(DgItemProDep record);

    DgItemProDep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemProDep record);

    int updateByPrimaryKey(DgItemProDep record);
    
    List<DgItemProDep> getAllData(Map<String,Object>  record);
    void deleteIds(List<Integer> ids);
    int deleteAll();
    int insertBackId(DgItemProDep record);
    int getCountByItemId(Integer id);
    DgItemProDep selectByItemId(Integer id);
    
    int deleteByItemId(int id);
}