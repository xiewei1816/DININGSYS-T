package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPricePriority;
@Repository
public interface DgItemPricePriorityMapper extends GenericDao<DgItemPricePriority,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemPricePriority record);

    int insertSelective(DgItemPricePriority record);

    DgItemPricePriority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemPricePriority record);

    int updateByPrimaryKey(DgItemPricePriority record);
    
    
    /**
     * 
     * 
     */
    List<DgItemPricePriority> getAll(); //获取所有数据按index排序
    int updateAll();
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
    void insertBatch(@Param("listObj") List<DgItemPricePriority> listObj);
}