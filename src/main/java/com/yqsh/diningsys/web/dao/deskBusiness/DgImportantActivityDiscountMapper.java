package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
@Repository
public interface DgImportantActivityDiscountMapper extends GenericDao<DgImportantActivityDiscount,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgImportantActivityDiscount record);

    int insertSelective(DgImportantActivityDiscount record);

    DgImportantActivityDiscount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgImportantActivityDiscount record);

    int updateByPrimaryKey(DgImportantActivityDiscount record);
    
    int insertBackId(DgImportantActivityDiscount record);
    
    
    /**
     * 
     * 分页
     */
    int countAllData(DgImportantActivityDiscount record); //获取总数量
    List<DgImportantActivityDiscount> getAllData(DgImportantActivityDiscount record);
    void deleteIds(List<Integer> ids);
    void trash(List<Integer> ids);
    void restore(List<Integer> ids);
    List<DgImportantActivityDiscount> seleAll();
    List<Map<String,Object>> reminder();
    void updateDisable(Integer id);
    
    int seleNameCode(DgImportantActivityDiscount src);
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
    void insertBatch(@Param("listObj") List<DgImportantActivityDiscount> listObj);
}