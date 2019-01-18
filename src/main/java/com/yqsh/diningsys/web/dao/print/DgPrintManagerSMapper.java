package com.yqsh.diningsys.web.dao.print;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.print.DgPrintManager;
import com.yqsh.diningsys.web.model.print.DgPrintManagerS;

public interface DgPrintManagerSMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgPrintManagerS record);

    int insertSelective(DgPrintManagerS record);

    DgPrintManagerS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgPrintManagerS record);

    int updateByPrimaryKey(DgPrintManagerS record);
    
    
    
    List<DgPrintManagerS>  selectItemByPid(Integer id);
    
    List<DgPrintManagerS>  selectItemTypeByPid(Integer id);
    
    void deleteIds(List<Integer> ids);
    
    int deleteByItemId(Map<String,Object> id);
    
    void insertChilds(List<DgPrintManagerS> lists);
    
    List<DgItemFile> selectItemByAdd(List ids);
    
    List<DgItemFileType> selectItemTypeByAdd(List ids);
    
    
    List<DgPrintManager> selectPrintManagerByItem(Map orgs);
}