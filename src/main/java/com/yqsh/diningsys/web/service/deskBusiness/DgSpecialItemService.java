package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpecialItem;

public interface DgSpecialItemService extends GenericService<DgSpecialItem,Integer>{
    /**
     * 
     * 获取所有数据
     */
    List<DgSpecialItem> getAll();
    
    int deleteAll(); //删除所有数据
    int deleteByItemId(int id);
    DgSpecialItem seleByItemId(int id);
}