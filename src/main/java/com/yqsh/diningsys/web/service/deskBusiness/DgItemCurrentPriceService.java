package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCurrentPrice;

public interface DgItemCurrentPriceService  extends GenericService<DgItemCurrentPrice,Integer>{
    List<DgItemCurrentPrice> getAllData(DgItemCurrentPrice record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemFile> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(String s);
    void deleteNotIn(List<Integer> ids);
    int deleteByItemId(int id);
    DgItemCurrentPrice selectByItemId(Integer id);
    int insertChilds(List<DgItemCurrentPrice> record);
    int saveItemCurrPric(String data);
    void synItemCurrentPrice(List<DgItemCurrentPrice> listItemCurrPrice);
}
