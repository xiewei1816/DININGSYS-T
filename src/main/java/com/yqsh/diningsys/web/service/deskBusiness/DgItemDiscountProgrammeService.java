package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;

public interface DgItemDiscountProgrammeService extends GenericService<DgItemDiscountProgramme,Integer>{
    List<DgItemFile>  selectAllItemFile(String ids,String disable,String yxdz);
    List<DgItemFile>  selectSmallItemFile(String ids,int ppxl_id,String disable,String yxdz);
    List<DgItemFile>  selectBigItemFile(String ids,int ppdl_id,String disable,String yxdz);
    List<DgItemFile>  selectHaveItem(String ids,String disable,String yxdz);
    List<DgItemFile>  searchVague(String id,String search,String ids,String disable,String yxdz);
    List<DgItemFile>  search(String search);
    List<DgItemDiscountProgramme>  selectAllProgrammes();
    int insertBackId(DgItemDiscountProgramme record);
    
    
    void deleteIds(String s);
    
    
    List<DgItemFileType>  selectAllItemFileType(String ids);
    List<DgItemFileType>  selectSmallItemFileType(String ids,int parent_id);
    List<DgItemFileType>  selectHaveItemType(String ids);
    
    
    List<DgItemFile>  selectAllDgItemFile();
    List<DgItemFile>  selectSmallDgItemFile(Integer id);
    List<DgItemFile>  selectBigDgItemFile(Integer id);
    
    void trash(String s);
    void restore(String s);
    
    //分页查询
    Page<DgItemDiscountProgramme> getPage(DgItemDiscountProgramme dgItemDiscountProgramme);
    List<Map<String,Object>> reminder();
    
    int seleNameCode(DgItemDiscountProgramme src);
    /**
     * 品项打折方案数据同步
     * @author jianglei
     * 日期 2017年2月13日 下午1:34:10
     * @param listItemDiscPro
     * @param listItemDiscPros
     * @param listWeekDisc
     */
    void synItemDiscPro(List<DgItemDiscountProgramme> listItemDiscPro,
                        List<DgItemDiscountProgrammeS> listItemDiscPros, List<DgWeekDiscount> listWeekDisc);
}
