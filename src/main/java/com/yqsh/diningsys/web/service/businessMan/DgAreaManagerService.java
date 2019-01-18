package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;

public interface DgAreaManagerService extends GenericService<DgAreaManager,Integer>{
    int selectCountByAreaId(Integer id);
    DgAreaManager selectByAreaId(Integer id);
    int insertBackId(DgAreaManager record);
    List<DgAreaManager> selectAll();
    int getAllCount();
}