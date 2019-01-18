package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPricePriority;

public interface DgItemPricePriorityService extends GenericService<DgItemPricePriority,Integer>{
    List<DgItemPricePriority> getAll(); //获取所有数据按index排序
    int updateAll();
    int synItemPricPrio(List<DgItemPricePriority> listItemPricPrio);
}