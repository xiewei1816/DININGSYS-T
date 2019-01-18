package com.yqsh.diningsys.web.service.sysSettings;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.sysSettings.SysBackupDatabase;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;

public interface SysBackupDataBaseService extends GenericService<SysBackupDatabase,Integer> {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
    Page<SysBackupDatabase> getListByPage(SysBackupDatabase page);
    
    int countListByPage(SysBackupDatabase page);
    
    int deleteData(String ids);
    
    List<SysBackupDatabase> selectFromIds(String ids);
}
