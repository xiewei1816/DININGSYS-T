package com.yqsh.diningsys.web.dao.online;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.online.DgTakeoutByonline;
@Repository
public interface DgTakeoutByonlineMapper{
    int deleteByPrimaryKey(String id);

    int insert(DgTakeoutByonline record);

    int insertSelective(DgTakeoutByonline record);

    DgTakeoutByonline selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DgTakeoutByonline record);

    int updateByPrimaryKeyWithBLOBs(DgTakeoutByonline record);

    int updateByPrimaryKey(DgTakeoutByonline record);
    
    int insertOrEdit_takeout_online(@Param("list") List<Map<String,Object>>  date);
    
    
    List<DgTakeoutByonline> selectTop20();
    
    int updateIds(List date);
}