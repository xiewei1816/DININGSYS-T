package com.yqsh.diningsys.web.dao.businessMan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;
@Repository
public interface DgAreaLimitItemMapper extends GenericDao<DgAreaLimitItem,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(DgAreaLimitItem record);

    int insertSelective(DgAreaLimitItem record);

    DgAreaLimitItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgAreaLimitItem record);

    int updateByPrimaryKey(DgAreaLimitItem record);
    
    
    List<DgAreaLimitItem> getByAreaId(Integer id);
    int deleteIds(List<Integer> ids);
    int deleteByAreaId(Integer id);   
}