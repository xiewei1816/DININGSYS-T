package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;

public interface DgAreaLimitItemService extends GenericService<DgAreaLimitItem,Integer>{
    List<DgAreaLimitItem> getByAreaId(Integer id);
    int deleteIds(String s);
    int deleteByAreaId(Integer id);  
}
