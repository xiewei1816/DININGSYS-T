package com.yqsh.diningsys.web.dao.sysSettings;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

/**
 * 创建时间 2017-08-04 17:50
 *
 * @author zhshuo
 */
@Repository
public interface SysHelpDao {

    LinkedList<String> selectWithBegtinTimeOrEndTime(@Param("defaultDataBaseName") String defaultDataBaseName, @Param("queryFlag") Integer queryFlag);

    String selectWithSingleTime(@Param("defaultDataBaseName") String defaultDataBaseName, @Param("queryTime") String queryTime);

}
