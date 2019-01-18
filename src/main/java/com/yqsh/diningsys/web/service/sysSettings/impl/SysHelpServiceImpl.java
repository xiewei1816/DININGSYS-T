package com.yqsh.diningsys.web.service.sysSettings.impl;

import com.yqsh.diningsys.web.dao.sysSettings.SysHelpDao;
import com.yqsh.diningsys.web.service.sysSettings.SysHelpService;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建时间 2017-08-04 17:50
 *
 * @author zhshuo
 */
@Service
public class SysHelpServiceImpl implements SysHelpService {

    @Resource
    private SysHelpDao sysHelpDao;

    private void tableNameSplit(List<String> tableSuffix, String tableName){
        String dateSuffix = tableSuffixGet(tableName);
        tableSuffix.add(dateSuffix);
    }

    private String tableSuffixGet(String tableName){
        if(StringUtils.isEmpty(tableName)){
            return null;
        }
        String[] split = tableName.split("_");
        String dateSuffix = split[split.length-2]+"_"+split[split.length-1];
        return dateSuffix;
    }

    @Override
    public LinkedList<String> selectWithBegtinTime(String defaultDataBaseName, String beginDateOrEndTime, Integer queryFlag) {
        LinkedList<String> allTables = sysHelpDao.selectWithBegtinTimeOrEndTime(defaultDataBaseName,queryFlag);

        LinkedList<String> tableSuffix = new LinkedList<>();

        if(beginDateOrEndTime != null){
            Integer yearMothToInteger = TableQueryUtil.yearMothToInteger_(beginDateOrEndTime);

            for(int i = 0;i < allTables.size();i++){
                String tableName = allTables.get(i);

                String tableSuffix_2 = tableSuffixGet(tableName);

                Integer tableSuffixInteger = TableQueryUtil.yearMothToInteger_(tableSuffix_2);

                if(queryFlag == 1) {
                    if (tableSuffixInteger >= yearMothToInteger) {
                        tableSuffix.add(tableSuffix_2);
                    }
                }else if(queryFlag == 2){
                    if(tableSuffixInteger <= yearMothToInteger){
                        tableSuffix.add(tableSuffix_2);
                    }
                }

            }
        }else{
            for(int i = 0;i < allTables.size();i++){
                String tableName = allTables.get(i);

                String tableSuffix_2 = tableSuffixGet(tableName);

                tableSuffix.add(tableSuffix_2);
            }
        }

        return tableSuffix;
    }

    @Override
    public String selectWithSingleTime(String defaultDataBaseName, String time) {
        String tableName = "dg_reception_clearing_water"+"_"+time;
        String existTable = sysHelpDao.selectWithSingleTime(defaultDataBaseName,tableName);
        return tableSuffixGet(existTable);
    }

    @Override
    public LinkedList<String> selectWithBegtinTimeAndEndTime(String defaultDataBaseName, String beginDate, String endDate, LinkedList<String> rangeMonth) {
        LinkedList<String> allTables = sysHelpDao.selectWithBegtinTimeOrEndTime(defaultDataBaseName,0);
        LinkedList<String> tablePrefix = new LinkedList<>();

        if(rangeMonth.size() < 1){
            return null;
        }

        Integer firstMonth = TableQueryUtil.yearMothToInteger(rangeMonth.getFirst());

        Integer lastMonth = TableQueryUtil.yearMothToInteger(rangeMonth.getLast());

        for(int i = 0;i < allTables.size();i++){
            String tableName = allTables.get(i);

            String tableSuffix = tableSuffixGet(tableName);

            Integer tableSuffixInteger = TableQueryUtil.yearMothToInteger_(tableSuffix);

            if(tableSuffixInteger >= firstMonth && tableSuffixInteger <= lastMonth){
                tablePrefix.add(tableSuffix);
            }

        }
        return tablePrefix;
    }
}
