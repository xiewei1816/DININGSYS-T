package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.print.DgPrintManagerS;

public interface DgItemDiscountProgrammeSService extends GenericService<DgItemDiscountProgrammeS,Integer>{
    List<DgItemDiscountProgrammeS>  selectItemByPid(Integer id);
    List<DgItemDiscountProgrammeS>  selectItemTypeByPid(Integer id);
    List<DgItemFile>  selectItemByAdd(String id);
    List<DgItemFileType> selectItemTypeByAdd(String id);
    void deleteIds(String s);
    int deleteByItemId(Map<String,Object> id);
//    Map<String,Object> selectByPIdAndItemIdType1(Integer src);
//    Map<String,Object> selectByPIdAndItemIdType2(Integer src);
    
    
    /**
     * 批量插入字项
     */
    void insertChilds(List<DgItemDiscountProgrammeS> lists);
    
}
