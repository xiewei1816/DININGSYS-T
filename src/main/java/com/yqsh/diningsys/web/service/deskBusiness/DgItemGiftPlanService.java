package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;

public interface DgItemGiftPlanService extends GenericService<DgItemGiftPlan,Integer>{
    int insertBackId(DgItemGiftPlan record);
    Page<DgItemGiftPlan> getPageGiftPlan(DgItemGiftPlan dgItemGiftPlan);
    void deleteIds(String s);
    DgItemGiftPlan getDataByPrimaryKey(Integer id);
    int deleteByItemId(int id);
    List<DgItemGiftPlan> selectAll();
    
    void trash(String ids);
    void restore(String ids);
    List<Map<String,Object>> reminder();
    
    int seleNameCode(DgItemGiftPlan src);
    void synItemGiftPlan(List<DgItemGiftPlan> listGiftPan,List<DgItemGiftPlanS> listGiftPans);
}