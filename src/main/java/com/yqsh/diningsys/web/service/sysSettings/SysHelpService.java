package com.yqsh.diningsys.web.service.sysSettings;

import java.util.LinkedList;

/**
 * 创建时间 2017-08-04 17:47
 *
 * @author zhshuo
 */
public interface SysHelpService {

    LinkedList<String> selectWithBegtinTime(String defaultDataBaseName, String beginDate, Integer queryFlag);

    String selectWithSingleTime(String defaultDataBaseName, String time);

    LinkedList<String> selectWithBegtinTimeAndEndTime(String defaultDataBaseName, String beginDate, String endDate, LinkedList<String> rangeMonth);

}
