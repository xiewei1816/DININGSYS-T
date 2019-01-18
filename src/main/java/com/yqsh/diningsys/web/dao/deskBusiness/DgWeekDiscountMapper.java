package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;
@Repository
public interface DgWeekDiscountMapper extends GenericDao<DgWeekDiscount,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgWeekDiscount record);

    int insertSelective(DgWeekDiscount record);

    DgWeekDiscount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgWeekDiscount record);

    int updateByPrimaryKey(DgWeekDiscount record);
    
    
    List<DgWeekDiscount> selectAll();
    
    int updataByDelete(List<Integer> ids);
    
    DgWeekDiscount selectByName(String name);
    
    
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
    void insertBatch(@Param("listObj") List<DgWeekDiscount> listObj);
}