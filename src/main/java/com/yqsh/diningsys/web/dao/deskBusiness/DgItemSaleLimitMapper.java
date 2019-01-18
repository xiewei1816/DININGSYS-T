package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
@Repository
public interface DgItemSaleLimitMapper extends GenericDao<DgItemSaleLimit,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemSaleLimit record);

    int insertSelective(DgItemSaleLimit record);

    DgItemSaleLimit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemSaleLimit record);

    int updateByPrimaryKey(DgItemSaleLimit record);
    
    /**
     *  根据事件获取id
     */
    DgItemSaleLimit selectByDate(String date);
    
    /**
     * 查询是否当日数据存在
     */
    int getCountByData(String date);
    int insertBackId(DgItemSaleLimit record);
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
    void insertBatch(@Param("listObj") List<DgItemSaleLimit> listObj);
}