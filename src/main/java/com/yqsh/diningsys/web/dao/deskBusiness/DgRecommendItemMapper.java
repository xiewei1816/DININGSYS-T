package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgRecommendItem;
@Repository
public interface DgRecommendItemMapper extends GenericDao<DgRecommendItem,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgRecommendItem record);

    int insertSelective(DgRecommendItem record);

    DgRecommendItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgRecommendItem record);

    int updateByPrimaryKey(DgRecommendItem record);
    
    List<DgRecommendItem> selectAll(DgRecommendItem record);
    
    List<DgRecommendItem> selectItemByAdd(List<Integer> s);
    
    int insertMore(List<DgRecommendItem> s);
    
    
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
    void insertBatch(@Param("listObj") List<DgRecommendItem> listObj);
}