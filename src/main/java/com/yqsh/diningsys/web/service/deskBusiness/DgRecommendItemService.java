package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgRecommendItem;

public interface DgRecommendItemService extends GenericService<DgRecommendItem,Integer>{
    List<DgRecommendItem> selectAll(DgRecommendItem record);
    List<DgRecommendItem> selectItemByAdd(String ids);
    int insertMore(String s);
    
    void deleteIds(String ids);//批量删除
    void deleteAll();//批量删除
    int deleteByItemId(int id);
    void synRecommendItem(List<DgRecommendItem> listRecommendItem);
}
