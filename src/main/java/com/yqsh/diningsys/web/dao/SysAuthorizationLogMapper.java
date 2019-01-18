package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2017-05-19 10:59
 *
 * @author zhshuo
 */
@Repository
public interface SysAuthorizationLogMapper {

    Integer insertAuthLog(SysAuthorizationLog sysAuthorizationLog);

    Integer countAllData(SysAuthorizationLog record);

    List<SysAuthorizationLog> getAllData(SysAuthorizationLog record);
}
