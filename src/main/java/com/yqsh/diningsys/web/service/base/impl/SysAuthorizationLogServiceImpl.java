package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.SysAuthorizationLogMapper;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.service.base.SysAuthorizationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2017-05-19 11:43
 *
 * @author zhshuo
 */
@Service
public class SysAuthorizationLogServiceImpl implements SysAuthorizationLogService {

    @Resource
    private SysAuthorizationLogMapper sysAuthorizationLogMapper;

    @Override
    public Integer insertAuthLog(SysAuthorizationLog sysAuthorizationLog) {
        return sysAuthorizationLogMapper.insertAuthLog(sysAuthorizationLog);
    }

    @Override
    public Page<SysAuthorizationLog> getAllData(SysAuthorizationLog record) {
        Integer totle = sysAuthorizationLogMapper.countAllData(record);
        List<SysAuthorizationLog> list = sysAuthorizationLogMapper.getAllData(record);
        return PageUtil.getPage(totle, record.getPage(),list, record.getRows());
    }
}
