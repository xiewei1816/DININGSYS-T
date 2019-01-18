package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;

public interface DgItemGiftPlanSService extends GenericService<DgItemGiftPlanS,Integer>{
    List<DgItemGiftPlanS> seleByPid(Integer id);
    void deleteIds(String s);
    int deleteByItemId(int id);
    void insertChilds(List<DgItemGiftPlanS> src);
}
