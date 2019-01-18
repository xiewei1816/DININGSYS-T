package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;
@Repository
public interface DgItemDisableMapper extends GenericDao<DgItemDisable,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemDisable record);

    int insertSelective(DgItemDisable record);

    DgItemDisable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemDisable record);

    int updateByPrimaryKey(DgItemDisable record);
    
    
    
    List<DgItemDisable> getAllData(DgItemDisable record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemDisable> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(List<Integer> ids);
    void deleteNotIn(List<Integer> ids);
    DgItemDisable seleByItemId(Integer id);
    int insertChilds(List<DgItemDisable> record);
    int deleteByItemKey(@Param("itemId")Integer itemId);
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
    void insertBatch(@Param("listObj") List<DgItemDisable> listObj);
}