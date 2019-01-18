package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;

public interface DgSeatItemService extends GenericService<DgSeatItem,Integer>{
    List<DgSeatItem> getBySeatId(Integer id);
    int deleteIds(String s);
    int deleteBySeatId(Integer id);   
}