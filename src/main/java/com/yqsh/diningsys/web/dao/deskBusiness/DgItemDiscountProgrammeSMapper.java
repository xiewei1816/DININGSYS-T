package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
@Repository
public interface DgItemDiscountProgrammeSMapper extends GenericDao<DgItemDiscountProgrammeS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemDiscountProgrammeS record);

    int insertSelective(DgItemDiscountProgrammeS record);

    DgItemDiscountProgrammeS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemDiscountProgrammeS record);

    int updateByPrimaryKey(DgItemDiscountProgrammeS record);
    
    List<DgItemDiscountProgrammeS>  selectItemByPid(Integer id);
    
    List<DgItemDiscountProgrammeS>  selectItemTypeByPid(Integer id);
    
    
    List<DgItemFile>  selectItemByAdd(List<Integer> ids);
    
    List<DgItemFileType> selectItemTypeByAdd(List<Integer> ids);
    
    void deleteIds(List<Integer> ids);
    
    int deleteByItemId(Map<String,Object> id);
    
    Map<String,Object> selectByPIdAndItemIdType1(Map orgs);
    Map<String,Object> selectByPIdAndItemIdType2(Map orgs);
    
    /**
     * 批量插入字项
     */
    void insertChilds(List<DgItemDiscountProgrammeS> lists);
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
    void insertBatch(@Param("listObj") List<DgItemDiscountProgrammeS> listObj);
}