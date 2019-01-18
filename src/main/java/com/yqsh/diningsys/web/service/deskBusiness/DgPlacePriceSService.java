package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;

public interface DgPlacePriceSService extends GenericService<DgPlacePriceS,Integer>{
    List<DgPlacePriceS>  selectByPlacePriceId(Integer id);
    void deleteIds(String s);
    int deleteAll();
    int deleteByItemId(int id);
    /**
     * 按消费区域和品项id查询
     * @param src
     * @return
     */
    DgPlacePriceS selectByItemIdAndPriceId(Map src);
    int insertChilds(List<DgPlacePriceS> record);
}
