package com.yqsh.diningsys.web.dao.api;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.api.SysBusinessLog;
@Repository
public interface SysBusinessLogMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(SysBusinessLog record);

    int insertSelective(SysBusinessLog record);

    SysBusinessLog selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(SysBusinessLog record);

    int updateByPrimaryKeyWithBLOBs(SysBusinessLog record);

    int updateByPrimaryKey(SysBusinessLog record);
    
    
    
    int getCount(SysBusinessLog orgs);
    
    List<SysBusinessLog>  getListByPage(SysBusinessLog orgs);
}