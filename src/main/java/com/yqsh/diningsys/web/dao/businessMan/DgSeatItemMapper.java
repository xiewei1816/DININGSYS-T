package com.yqsh.diningsys.web.dao.businessMan;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface DgSeatItemMapper extends GenericDao<DgSeatItem,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgSeatItem record);

    int insertSelective(DgSeatItem record);

    DgSeatItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgSeatItem record);

    int updateByPrimaryKey(DgSeatItem record);
    
    
    
    List<DgSeatItem> getBySeatId(Integer id);
    int deleteIds(List<Integer> ids);
    int deleteBySeatId(Integer id);   
    DgSeatItem getBySeatIdAndItemId(Map orgs);
}