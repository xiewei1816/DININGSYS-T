package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;

public interface DgSeatManagerService extends GenericService<DgSeatManager,Integer>{
    DgSeatManager selectBySeatId(Integer id);
    List<DgSeatManager> selectDetailBySeatId(Integer id);
    List<DgSeatManager> selectAllDetailBySeatId();
    
    int getAllCount();//获取所有数量
    int getCountByAreaId(Integer id);
}