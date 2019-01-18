package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
@Repository
public interface DgItemGiftPlanMapper extends GenericDao<DgItemGiftPlan,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemGiftPlan record);

    int insertSelective(DgItemGiftPlan record);

    DgItemGiftPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemGiftPlan record);

    int updateByPrimaryKey(DgItemGiftPlan record);
    
    
    int insertBackId(DgItemGiftPlan record);
    /**
     * 
     * 分页
     */
    int countAllData(DgItemGiftPlan record); //获取总数量
    List<DgItemGiftPlan> getAllData(DgItemGiftPlan record);
    List<DgItemGiftPlan> selectAll();
    void deleteIds(List<Integer> ids);
    DgItemGiftPlan getDataByPrimaryKey(Integer id);
    
    int deleteByItemId(int id);
    
    
    void trash(List<Integer> ids);
    void restore(List<Integer> ids);
    List<Map<String,Object>> reminder();
    int seleNameCode(DgItemGiftPlan src);
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
    void insertBatch(@Param("listObj") List<DgItemGiftPlan> listObj);
}