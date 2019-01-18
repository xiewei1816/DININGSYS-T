package com.yqsh.diningsys.web.service.permission;


import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.permission.DgFrontDeskOperQx;
import com.yqsh.diningsys.web.model.permission.DgFrontDeskOperQxArray;

public interface DgFrontDeskOperQxService extends GenericService<DgFrontDeskOperQx,Integer>{
    List<DgFrontDeskOperQx> seleAll();
    int updateByPrimaryKeyInIds(String ids);
    int updateByPrimaryKeyNotInIds(String ids);
    int saveFrontDeskQx(DgFrontDeskOperQxArray qxs);
}