package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
@Repository
public interface DgPlacePriceSMapper extends GenericDao<DgPlacePriceS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgPlacePriceS record);

    int insertSelective(DgPlacePriceS record);

    DgPlacePriceS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgPlacePriceS record);

    int updateByPrimaryKey(DgPlacePriceS record);
    
    
    List<DgPlacePriceS>  selectByPlacePriceId(Integer id);
    void deleteIds(List<Integer> ids);
    int deleteAll();
    int deleteByItemId(int id);
    @SuppressWarnings("rawtypes")
	DgPlacePriceS selectByItemIdAndPriceId(Map src);
    
    int insertChilds(List<DgPlacePriceS> record);
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
    void insertBatch(@Param("listObj") List<DgPlacePriceS> listObj);
}