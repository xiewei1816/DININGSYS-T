package com.yqsh.diningsys.web.service.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.print.DgPrintManager;
import com.yqsh.diningsys.web.model.print.DgPrintManagerS;

public interface DgPrintManagerService extends GenericService<DgPrintManager,Integer>{
    Page<DgPrintManager> getPage(DgPrintManager dgPrintManager);
    void trash(String s);
    void restore(String s);
    void deleteIds(String s);
    void deleteChildByPid(String s);
    int insertBackId(DgPrintManager record);
    
    
    List<DgItemFile>  selectAllItemFile(String ids);
    List<DgItemFile>  selectSmallItemFile(String ids,int ppxl_id);
    List<DgItemFile>  selectBigItemFile(String ids,int ppdl_id);
    List<DgItemFile>  searchVague(String search,String ids);
    List<DgItemFile>  selectHaveItem(String ids);
    
    List<DgItemFileType> selectAllItemFileType(String s);
    List<DgItemFileType> selectHaveItemType(String s);
    List<DgItemFileType> selectSmallItemFileType(String ids,Integer pId);
    
    /**
     * 批量插入字项
     */
    void insertChilds(List<DgPrintManagerS> lists);
    
    
    List<DgPrintManagerS> selectItemByPid(Integer id);
    
    List<DgPrintManagerS>  selectItemTypeByPid(Integer id);
    
    List<DgItemFile> selectItemByAdd(String s);
    
    List<DgItemFileType> selectItemTypeByAdd(String s);
    
    List<DgConsumptionArea> getAreaByIds(String s);
    
    List<DgPrintManager> selectPrintManagerByItem(Map orgs);
}
