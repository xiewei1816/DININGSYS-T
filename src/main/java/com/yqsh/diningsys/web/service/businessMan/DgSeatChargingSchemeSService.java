package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;

public interface DgSeatChargingSchemeSService extends GenericService<DgSeatChargingSchemeS,Integer>{
    List<DgSeatChargingSchemeS> seleByPid(Integer id);
    List<DgSeatChargingSchemeS> seleByPidNoSd(Integer id);
    void deleteIds(String s);
    void deleteAllByPid(Integer id);
}
