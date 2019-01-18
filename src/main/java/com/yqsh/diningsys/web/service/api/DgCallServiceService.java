package com.yqsh.diningsys.web.service.api;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.api.DgCallService;

public interface DgCallServiceService {
    int deleteByPrimaryKey(Integer id);

    int insert(DgCallService record);

    int insertSelective(DgCallService record);

    DgCallService selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgCallService record);

    int updateByPrimaryKey(DgCallService record);
    
    List<DgCallService> selectTop5(Map orgs);
   
    List<DgCallService> selectTopZccf5();
    
    int deleteIds(List ids);
    
    Integer selectOnlineCount();
    
    DgCallService getFirstItem();

    int selectCxptCount();

    List<DgCallService> selectCxptTop5();

    DgCallService getFirstCxptItem();
}
