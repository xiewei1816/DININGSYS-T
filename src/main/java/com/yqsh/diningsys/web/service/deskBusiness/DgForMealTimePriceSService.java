package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;

public interface DgForMealTimePriceSService extends GenericService<DgForMealTimePriceS,Integer>{
    List<DgForMealTimePriceS>  selectByMealPriceId(Integer id);
    void deleteIds(String s);
    int deleteAll();
    int deleteByItemId(int id);
    DgForMealTimePriceS selectByItemIdAndMealtime(Map src);
}