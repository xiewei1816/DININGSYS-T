package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgPromotionItem;
@Repository
public interface DgItemTimeLimitMapper extends GenericDao<DgItemTimeLimit,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemTimeLimit record);

    int insertSelective(DgItemTimeLimit record);

    DgItemTimeLimit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemTimeLimit record);

    int updateByPrimaryKey(DgItemTimeLimit record);
    
    
    List<DgItemTimeLimit> getAllData(DgItemTimeLimit record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemFile> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(List<Integer> ids);
    void deleteNotIn(List<Integer> ids);
    
    int deleteByItemId(int id);
    
    DgItemTimeLimit selectByItemId(Integer id);
    int insertChilds(List<DgItemTimeLimit> record);
    DgItemTimeLimit getOne();
    
    /**
     * 查询当前时间,指定品项下是否存在限时抢购
     */
    DgItemTimeLimit selectByItemIdAndOnTime(Integer ItemId);  
}