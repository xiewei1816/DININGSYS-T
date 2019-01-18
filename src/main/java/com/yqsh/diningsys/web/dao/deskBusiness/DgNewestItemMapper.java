package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
@Repository
public interface DgNewestItemMapper extends GenericDao<DgNewestItem,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgNewestItem record);

    int insertSelective(DgNewestItem record);

    DgNewestItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgNewestItem record);

    int updateByPrimaryKey(DgNewestItem record);
    
    List<DgNewestItem> selectAll(DgNewestItem record);
    
    List<DgNewestItem> selectItemByAdd(List<Integer> s);
    
    int insertMore(List<DgNewestItem> s);
    
    
    void deleteIds(List<Integer> ids);//批量删除
    void deleteAll();//批量删除
    
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
    void insertBatch(@Param("listObj") List<DgNewestItem> listObj);
}