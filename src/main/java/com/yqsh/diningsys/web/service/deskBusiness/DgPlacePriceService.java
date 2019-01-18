package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;

public interface DgPlacePriceService extends GenericService<DgPlacePrice,Integer>{
    List<DgPlacePrice> getAllData(DgPlacePrice record);
    void deleteIds(String s);
    int deleteAll();
    int insertBackId(DgPlacePrice record);
    int deleteByItemId(int id);
    int insertChilds(List<DgPlacePrice> record);
    void synPlacePrice(List<DgPlacePrice> listPlacPric,List<DgPlacePriceS> listPlacPrics);
    int savePlacePrice(String data);
}
