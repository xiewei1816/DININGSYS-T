package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;

public interface DgItemDisableService extends GenericService<DgItemDisable,Integer>{
    List<DgItemDisable> getAllData(DgItemDisable record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemDisable> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(String s);
    void deleteNotIn(List<Integer> ids);
    DgItemDisable seleByItemId(Integer id);
    int insertChilds(List<DgItemDisable> record);
    int saveItemDisable(String data);
    int deleteByItemKey(Integer itemId);
    void synItemDisable(List<DgItemDisable> listItemDisable);
}
