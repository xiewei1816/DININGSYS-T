package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
@Repository
public interface DgItemOutofStockMapper extends GenericDao<DgItemOutofStock,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemOutofStock record);

    int insertSelective(DgItemOutofStock record);

    DgItemOutofStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemOutofStock record);

    int updateByPrimaryKey(DgItemOutofStock record);
    
    List<DgItemOutofStock> getAllData(DgItemOutofStock record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemOutofStock> selectItemByAdd(List<Integer> ids);
    int deleteAll(int type);
    void deleteIds(List<Integer> ids);
    void deleteNotIn(List<Integer> ids);
    
    int deleteByItemId(int id);
    int deleteByType(DgItemOutofStock src);
    
    int selectByItemId(int id);
    int insertChilds(List<DgItemOutofStock> record);
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
    void insertBatch(@Param("listObj") List<DgItemOutofStock> listObj);
}