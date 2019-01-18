package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
@Repository
public interface DgItemDiscountProgrammeMapper extends GenericDao<DgItemDiscountProgramme,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemDiscountProgramme record);

    int insertSelective(DgItemDiscountProgramme record);

    DgItemDiscountProgramme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemDiscountProgramme record);

    int updateByPrimaryKey(DgItemDiscountProgramme record);
    
    
    
    List<DgItemFile>  selectAllItemFile(Map src);
    List<DgItemFile>  selectSmallItemFile(HashMap<String, Object> src);
    List<DgItemFile>  selectBigItemFile(HashMap<String, Object> src);
    List<DgItemFile>  searchVague(HashMap<String, Object> src);
    List<DgItemFile>  search(String search);
    List<DgItemFile>  selectHaveItem(Map ids);
    
    List<DgItemDiscountProgramme>  selectAllProgrammes();
    
    
    int insertBackId(DgItemDiscountProgramme record);
    
    
    
    void deleteIds(List<Integer> ids);
    void trash(List<Integer> ids);
    void restore(List<Integer> ids);
    
    
    
    List<DgItemFileType>  selectAllItemFileType(List<Integer> ids);
    List<DgItemFileType>  selectSmallItemFileType(HashMap<String, Object> src);
    List<DgItemFileType>  selectHaveItemType(List<Integer> ids);
    
    
    int countAllData(DgItemDiscountProgramme record); //获取总数量
    List<DgItemDiscountProgramme> getAllData(DgItemDiscountProgramme record);
    
    /**
     * 
     * 
     */
    List<DgItemFile>  selectAllDgItemFile();
    List<DgItemFile>  selectSmallDgItemFile(Integer id);
    List<DgItemFile>  selectBigDgItemFile(Integer id);
    
    
   List<Map<String,Object>> reminder();
   
   int seleNameCode(DgItemDiscountProgramme src);
   int deleteByGateItemId(int id);
   /**
	 * 物理删除所有数据，此方法慎用
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:14:16
	 */
   void delPhy();
	/**
	 * 批量插入
	 * @author jianglei
	 * 日期 2017年1月18日 上午11:07:10
	 * @param listObj
	 */
    void insertBatch(@Param("listObj") List<DgItemDiscountProgramme> listObj);
}