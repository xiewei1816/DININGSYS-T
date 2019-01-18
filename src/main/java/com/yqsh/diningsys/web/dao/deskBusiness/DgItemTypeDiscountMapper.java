package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscountQuery;
@Repository
public interface DgItemTypeDiscountMapper extends GenericDao<DgItemTypeDiscount,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemTypeDiscount record);

    int insertSelective(DgItemTypeDiscount record);

    DgItemTypeDiscount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemTypeDiscount record);

    int updateByPrimaryKey(DgItemTypeDiscount record);
    
    /**
     * 
     * 更具小类id获取是否打折
     * @param id
     * @return
     */
    int getCountByItemType(Integer id);
    
    /*
     * 
     * 插入是否打折
     */
    void insertDiscountByItemType(Integer id);
    
    
    int getDiscountByDgId(Integer id);
    
    /*
     * 
     * 获取所有数据
     */
    
    List<DgItemTypeDiscountQuery> getAll();
    
    /**
     * 
     * 批量修改
     * @param record
     */
    void updateBySrcList(List<DgItemTypeDiscount> record);
    
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
    void insertBatch(@Param("listObj") List<DgItemTypeDiscount> listObj);
} 