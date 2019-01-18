package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;

public interface DgImportantActivityDiscountService extends GenericService<DgImportantActivityDiscount,Integer>{
    int insertBackId(DgImportantActivityDiscount record);
    Page<DgImportantActivityDiscount> getAllImportantActivity(DgImportantActivityDiscount dgImportantActivityDiscount);
    void deleteIds(String s);
    List<DgImportantActivityDiscount> seleAll();
    void trash(String s); //回收站
    void restore(String s); //还原
    List<Map<String,Object>> reminder();
    void updateDisable(Integer id);
    int seleNameCode(DgImportantActivityDiscount src);
    /**
     * 重要活动打折数据同步
     * @author jianglei
     * 日期 2017年2月14日 下午2:49:32
     * @param listDiad
     * @param listDiads
     */
    void synProImportant(List<DgImportantActivityDiscount> listDiad,
    		List<DgImportantAcitivityDiscountS> listDiads);
}
