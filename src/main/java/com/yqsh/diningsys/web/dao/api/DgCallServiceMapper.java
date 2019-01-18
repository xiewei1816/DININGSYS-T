package com.yqsh.diningsys.web.dao.api;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.api.DgCallService;
@Repository
public interface DgCallServiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgCallService record);

    int insertSelective(DgCallService record);

    DgCallService selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgCallService record);

    int updateByPrimaryKey(DgCallService record);
    
    List<DgCallService> selectTop5(Map orgs);
   
    List<DgCallService> selectTopZccf5();
    
    List<DgCallService> selectReserve();
    
    List<DgCallService> selectTop5Reserve();
    
    int deleteIds(List ids);
    
    int selectReserveCount();
    
    Integer selectOnlineCount();
    
    DgCallService getFirstItem();

    int selectCxptCount();

    List<DgCallService> selectCxptTop5();

    DgCallService getFirstCxptItem();
}