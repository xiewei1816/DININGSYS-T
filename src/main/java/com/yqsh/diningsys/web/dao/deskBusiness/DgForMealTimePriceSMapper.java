package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;
@Repository
public interface DgForMealTimePriceSMapper extends GenericDao<DgForMealTimePriceS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgForMealTimePriceS record);

    int insertSelective(DgForMealTimePriceS record);

    DgForMealTimePriceS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgForMealTimePriceS record);

    int updateByPrimaryKey(DgForMealTimePriceS record);
    
    
    List<DgForMealTimePriceS>  selectByMealPriceId(Integer id);
    void deleteIds(List<Integer> ids);
    int deleteAll();
    int deleteByItemId(int id);
    
    DgForMealTimePriceS selectByItemIdAndMealtime(Map src);
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
    void insertBatch(@Param("listObj") List<DgForMealTimePriceS> listObj);
}