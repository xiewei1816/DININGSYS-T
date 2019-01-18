package com.yqsh.diningsys.web.service.api;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.api.SysBusinessLog;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;

public interface APILogService {
	
	Page<SysBusinessLog> getListByPage(SysBusinessLog page);
}
