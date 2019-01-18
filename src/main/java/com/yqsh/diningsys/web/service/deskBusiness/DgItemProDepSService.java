package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;

public interface DgItemProDepSService extends GenericService<DgItemProDepS,Integer>{
    List<DgItemProDepS>  selectByPlaceParentId(Integer id);
    void deleteIds(String s);
    int deleteAll();
    int selectByEarchId(Map<String,Object> obj);
    int deleteByItemId(int id);
    int insertChilds(List<DgItemProDepS> record);
}