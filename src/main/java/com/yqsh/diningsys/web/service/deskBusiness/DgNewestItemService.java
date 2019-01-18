package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;

public interface DgNewestItemService extends GenericService<DgNewestItem,Integer>{
    List<DgNewestItem> selectAll(DgNewestItem record);
    List<DgNewestItem> selectItemByAdd(String ids);
    int insertMore(String s);
    
    void deleteIds(String ids);//批量删除
    void deleteAll();//批量删除
    
    int deleteByItemId(int id);
    void synNewestItem(List<DgNewestItem> listNewestItem);
}
