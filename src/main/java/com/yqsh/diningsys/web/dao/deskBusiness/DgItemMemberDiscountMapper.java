package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
@Repository
public interface DgItemMemberDiscountMapper extends GenericDao<DgItemMemberDiscount,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemMemberDiscount record);

    int insertSelective(DgItemMemberDiscount record);

    DgItemMemberDiscount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemMemberDiscount record);

    int updateByPrimaryKey(DgItemMemberDiscount record);
    
    
    
    int insertBackId(DgItemMemberDiscount record);
    /**
     * 
     * 分页
     */
    int countAllData(DgItemMemberDiscount record); //获取总数量
    List<DgItemMemberDiscount> getAllData(DgItemMemberDiscount record);
    List<DgItemMemberDiscount> seleAll();
    void deleteIds(List<Integer> ids);
    
    void update(Integer id);
    void updateLevelUnPulish(String levelId);
    
    void trash(List<Integer> ids);
    void restore(List<Integer> ids);
    List<Map<String,Object>> reminder();
    
    DgItemMemberDiscount selectByItem(DgItemMemberDiscount src);
    int seleNameCode(DgItemMemberDiscount src);
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
    void insertBatch(@Param("listObj") List<DgItemMemberDiscount> listObj);
}