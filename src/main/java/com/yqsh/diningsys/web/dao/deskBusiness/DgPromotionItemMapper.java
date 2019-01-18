package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPromotionItem;
@Repository
public interface DgPromotionItemMapper extends GenericDao<DgPromotionItem,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgPromotionItem record);

    int insertSelective(DgPromotionItem record);

    DgPromotionItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgPromotionItem record);

    int updateByPrimaryKey(DgPromotionItem record);
    
    
    List<DgPromotionItem> getAllData(DgPromotionItem record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemFile> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(List<Integer> ids);
    void deleteNotIn(List<Integer> ids);
    
    int deleteByItemId(int id);
    
    DgPromotionItem selectByItemId(Integer id);
    int insertChilds(List<DgPromotionItem> record);
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
    void insertBatch(@Param("listObj") List<DgPromotionItem> listObj);
}