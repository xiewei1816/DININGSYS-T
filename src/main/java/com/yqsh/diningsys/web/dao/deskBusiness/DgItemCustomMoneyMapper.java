package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.springframework.stereotype.Repository;




import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCustomMoney;
@Repository
public interface DgItemCustomMoneyMapper extends GenericDao<DgItemCustomMoney,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemCustomMoney record);

    int insertSelective(DgItemCustomMoney record);

    DgItemCustomMoney selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemCustomMoney record);

    int updateByPrimaryKey(DgItemCustomMoney record);
    
    
    List<DgItemCustomMoney> getAllByCustomNameId(int id);
    int deleteIds(List<Integer> ids);
    int deleteByItemIds(List<Integer> ids);
    int deleteByCustomId(Integer id);
    
    
    List<DgItemCustomMoney> selectAllByItemIds(List<Integer> ids);
    
    int deleteByItemId(int id);
    
    void insertChilds(List<DgItemCustomMoney> src);
}