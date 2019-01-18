package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.springframework.stereotype.Repository;




import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
@Repository
public interface BgItemCustomMoneyNameMapper extends GenericDao<BgItemCustomMoneyName,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(BgItemCustomMoneyName record);

    int insertSelective(BgItemCustomMoneyName record);

    BgItemCustomMoneyName selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BgItemCustomMoneyName record);

    int updateByPrimaryKey(BgItemCustomMoneyName record);
    
    /**
     * 
     * 获取所有
     */
    List<BgItemCustomMoneyName> getAll();
    int getCount(); //所有数目    
    
    
    int getCountByItemCode(Integer itemId);
    
    int updateByItemCode(BgItemCustomMoneyName record);
}