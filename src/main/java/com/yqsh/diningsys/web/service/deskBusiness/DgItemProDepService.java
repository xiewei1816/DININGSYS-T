package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;

public interface DgItemProDepService extends GenericService<DgItemProDep,Integer>{
    List<DgItemProDep> getAllData(Map<String,Object>  record);
    void deleteIds(String s);
    int deleteAll();
    int insertBackId(DgItemProDep record);
    int getCountByItemId(Integer id);
    DgItemProDep selectByItemId(Integer id);
    int deleteByItemId(int id);
}