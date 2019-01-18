package com.yqsh.diningsys.web.service.base;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;

/**
 * Created on 2017-05-19 11:42
 *
 * @author zhshuo
 */
public interface SysAuthorizationLogService {

    Integer insertAuthLog(SysAuthorizationLog sysAuthorizationLog);

    Page<SysAuthorizationLog> getAllData(SysAuthorizationLog record);
}
