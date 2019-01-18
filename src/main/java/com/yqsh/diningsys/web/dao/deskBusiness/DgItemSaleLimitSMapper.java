package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
@Repository
public interface DgItemSaleLimitSMapper extends GenericDao<DgItemSaleLimitS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemSaleLimitS record);

    int insertSelective(DgItemSaleLimitS record);

    DgItemSaleLimitS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemSaleLimitS record);

    int updateByPrimaryKey(DgItemSaleLimitS record);
    /**
     * 新增项
     */
    List<DgItemSaleLimitS> getAllData(Map<String,Object> record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemSaleLimitS> selectItemByAdd(List<Integer> ids);
    int deleteAll(Integer id);
    void deleteIds(List<Integer> ids);
    void deleteNotIn(List<Integer> ids);
    
    int deleteByItemId(int id);
    int updateCount(DgItemSaleLimitS useCount);
    int insertChilds(List<DgItemSaleLimitS> record);
    int updateCountList(List<DgItemSaleLimitS> useCount);
    /**
     * 根据相关条件删除数据
     * @author jianglei
     * 日期 2017年2月28日 上午10:53:14
     * @param itemId 品项id
     * @param limitId  主表id
     * @return
     */
    int deleteParams(@Param("itemId")Integer itemId,@Param("limitId")Integer limitId);
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
    void insertBatch(@Param("listObj") List<DgItemSaleLimitS> listObj);
}