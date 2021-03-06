package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPriceLadder;
@Repository
public interface DgItemPriceLadderMapper extends GenericDao<DgItemPriceLadder,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemPriceLadder record);

    int insertSelective(DgItemPriceLadder record);

    DgItemPriceLadder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemPriceLadder record);

    int updateByPrimaryKey(DgItemPriceLadder record);
    
    List<DgItemPriceLadder> getAllData(DgItemPriceLadder record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemFile> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(List<Integer> ids);
    void deleteNotIn(List<Integer> ids);
    
    int deleteByItemId(int id);
    DgItemPriceLadder selectByItemId(Integer id);
    int insertChilds(List<DgItemPriceLadder> record);
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
    void insertBatch(@Param("listObj") List<DgItemPriceLadder> listObj);
}