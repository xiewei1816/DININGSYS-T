package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;

public interface DgSeatChargingSchemeService extends GenericService<DgSeatChargingScheme,Integer>{
    int insertBackId(DgSeatChargingScheme record);
    Page<DgSeatChargingScheme> getPage(DgSeatChargingScheme dgSeatChargingScheme);
    void deleteIds(String s);
    List<DgSeatChargingScheme> seleAll(); 
    void trash(String s);
    void restore(String s);
}
