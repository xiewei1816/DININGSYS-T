package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;

public interface DgItemOutofStockService extends GenericService<DgItemOutofStock,Integer>{
    List<DgItemOutofStock> getAllData(DgItemOutofStock record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemOutofStock> selectItemByAdd(List<Integer> ids);
    int deleteAll(int type);
    void deleteIds(String s);
    void deleteNotIn(List<Integer> ids);
    int deleteByItemId(int id);
    int deleteByType(DgItemOutofStock src);
    int insertChilds(List<DgItemOutofStock> record);
    int saveItemOutofStock(String data);
    int synItemOutOfStock(List<DgItemOutofStock> listItemOutOfStock);
}