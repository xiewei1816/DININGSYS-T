package com.yqsh.diningsys.web.dao.sysSettings;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.sysSettings.SysBackupDatabase;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;
@Repository
public interface SysBackupDatabaseMapper extends GenericDao<SysBackupDatabase,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(SysBackupDatabase record);

    int insertSelective(SysBackupDatabase record);

    SysBackupDatabase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysBackupDatabase record);

    int updateByPrimaryKey(SysBackupDatabase record);
    
    /**
     * 分页查询
     * 
     */
    List<SysBackupDatabase> getListByPage(SysBackupDatabase page);
    
    int countListByPage(SysBackupDatabase page);
    
    int deletData(List<Integer> ids);
    
    
    List<SysBackupDatabase> selectFromIds(List<Integer> ids);
}