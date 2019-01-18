package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpecialItem;
@Repository
public interface DgSpecialItemMapper extends GenericDao<DgSpecialItem,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgSpecialItem record);

    int insertSelective(DgSpecialItem record);

    DgSpecialItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgSpecialItem record);

    int updateByPrimaryKey(DgSpecialItem record);
    
    /**
     * 
     * 获取所有数据
     */
    List<DgSpecialItem> getAll();
    
    int deleteAll(); //删除所有数据
    
    int deleteByItemId(int id);
    
    DgSpecialItem seleByItemId(int id);
}