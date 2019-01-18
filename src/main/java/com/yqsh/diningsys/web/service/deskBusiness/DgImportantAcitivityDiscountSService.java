package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;

public interface DgImportantAcitivityDiscountSService extends GenericService<DgImportantAcitivityDiscountS,Integer>{
    List<DgImportantAcitivityDiscountS> seleByPid(Integer id);
    void deleteIds(String s);
    int deleteByGateId(int id);
    Map<String, Object> selectByItemId(Integer id);
}
