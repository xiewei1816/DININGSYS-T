package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePrice;
@Repository
public interface DgForMealTimePriceMapper extends GenericDao<DgForMealTimePrice,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgForMealTimePrice record);

    int insertSelective(DgForMealTimePrice record);

    DgForMealTimePrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgForMealTimePrice record);

    int updateByPrimaryKey(DgForMealTimePrice record);
    
    List<DgForMealTimePrice> getAllData(DgForMealTimePrice record);
    void deleteIds(List<Integer> ids);
    
    int deleteAll();
    
    int insertBackId(DgForMealTimePrice record);
    
    int deleteByItemId(int id);
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
    void insertBatch(@Param("listObj") List<DgForMealTimePrice> listObj);
}